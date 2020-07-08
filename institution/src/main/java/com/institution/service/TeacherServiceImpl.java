package com.institution.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.institution.errorHandling.EntityNotFoundException;
import com.institution.messageSystem.MessageSender;
import com.institution.model.ApplicationUser;
import com.institution.model.Grade;
import com.institution.model.Teacher;
import com.institution.model.grade.TimeTable;
import com.institution.repository.GradeRepository;
import com.institution.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final MongoTemplate mongoTemplate;

    private final ImageService imageService;

    Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    public TeacherServiceImpl(MongoTemplate mongoTemplate, ImageService imageService) {
        this.mongoTemplate = mongoTemplate;
        this.imageService = imageService;
    }
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @Autowired
    UserServiceDao userService;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    MessageSender messageSender;

    @Override
    public Teacher createTeacher(Teacher teacher) {
        InstitutionSerivceImpl institutionSerivce = new InstitutionSerivceImpl();
        ApplicationUser user = new ApplicationUser();
        user.setId(sequenceGenerator.generateSequence(ApplicationUser.SEQUENCE_NAME));
        user.setRole("TEACHER");
        user.setPassword(institutionSerivce.generatePassword());
        user.setEmail(teacher.getEmail());
        teacher.setId(sequenceGenerator.generateSequence(Teacher.SEQUENCE_NAME));
        userService.createUser(user, teacher.getInstitutionId());
        teacher.setApplicationUserId(user.getId());
        String isClassTeacher = teacher.getClassTeacher() == null ? "false" : "true";
        teacher.setClassTeacher(Optional.ofNullable(teacher.getClassTeacher()).orElse("NOT ASSIGNED"));
        teacher.setClassTeacher(isClassTeacher);
        teacher.setPersisted(false);
        teacher.setTimeTable(generateTimeTable());
        if(teacher.getPicture()!=null) {
            //imageService.uploadFile(teacher.getPicture(), teacher.getName());
        }
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeacher(long institutionId) {
        return teacherRepository.findTeacherByInstitutionId(institutionId);
    }

    @Override
    /*public Teacher updateTeacher(Teacher teacher) {
        Optional<Teacher>  teacher1 = teacherRepository.findById(teacher.getId());
        Teacher updateTeacher = null;
        if(teacher1 == null) {
            throw new EntityNotFoundException(Teacher.class, "id", Long.toString(teacher.getId()));
        }else {
            teacher.setClassTeacher(teacher1.get().getClassTeacher());
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader objectReader = objectMapper.readerForUpdating(teacher1.get());
            try {
                updateTeacher= objectReader.readValue(objectMapper.writeValueAsString(teacher));
            }catch (Exception e) {
                e.printStackTrace();
            }
            return teacherRepository.save(updateTeacher);
        }

    }*/

    public Teacher updateTeacher(Teacher teacher, Long id) {
        Optional<Teacher>  teacherOptional = teacherRepository.findById(id);
        if(teacherOptional.get()!=null) {
            Teacher savedTeacher = teacherOptional.get();
            Long userId = savedTeacher.getApplicationUserId();
            String isClassTeacher = savedTeacher.getClassTeacher();
            savedTeacher = teacher;
            savedTeacher.setPersisted(true);
            savedTeacher.setId(id);
            savedTeacher.setApplicationUserId(userId);
            savedTeacher.setClassTeacher(isClassTeacher);
            return teacherRepository.save(savedTeacher);

        } else {
            throw new EntityNotFoundException(Teacher.class, "id", Long.toString(id));
        }

    }


    @Override
    public Page<Teacher> searchTeachers(Teacher teacher, long institutionId, Map<String,String> searchParam) {
        int pageNumber = Integer.parseInt(Optional.ofNullable(searchParam.get("pageNumber")).orElse("0"));
        int pageSize = Integer.parseInt(Optional.ofNullable(searchParam.get("?pageSize")).orElse("10"));
        Pageable page  =  PageRequest.of(pageNumber,pageSize);
        Set<String> subjects = new TreeSet<String>();
        Set<String> grades = new TreeSet<String>();
        if(!(teacher.getSubjects() == null) && !(teacher.getGrades() == null))
        {
            subjects.addAll(teacher.getSubjects());
            grades.addAll(teacher.getGrades());

            return teacherRepository.searchTeacherWithGradesAndGrades(Optional.ofNullable(teacher.getName()).orElse(""),
                    Optional.ofNullable(teacher.getEmail()).orElse("") ,
                    grades,
                    subjects,
                    institutionId, page);
        }

        if(!(teacher.getSubjects() == null)){
            subjects.addAll(teacher.getSubjects());

            return teacherRepository.searchTeacherWithSubjects(Optional.ofNullable(teacher.getName()).orElse(""),
                    Optional.ofNullable(teacher.getEmail()).orElse("") ,
                    subjects,
                    institutionId, page);
        }

        if(!(teacher.getGrades() == null)) {
            grades.addAll(teacher.getGrades());
            return teacherRepository.searchTeacherWithGrades(Optional.ofNullable(teacher.getName()).orElse(""),
                    Optional.ofNullable(teacher.getEmail()).orElse("") ,
                    grades,
                    institutionId, page);
        }

        else {
            return teacherRepository.searchTeacher(Optional.ofNullable(teacher.getName()).orElse(""),
                    Optional.ofNullable(teacher.getEmail()).orElse("") ,
                    institutionId, page);
        }
    }

    @Override
    public List<Teacher> findNonClassTeachers(long institutionId) {
        return teacherRepository.findAvailableTeachers(institutionId);
    }

    @Override
    public Optional<Teacher> findTeacherByGrade(Long institutionId, String grade, String name) {
       return  teacherRepository.findTeacherByInstitutionIdAndGradeAndName(institutionId, grade, name);
    }

    @Override
    public void deleteTeacher(long institutionId, long id) {
        Optional<Teacher> teacher = teacherRepository.findTeacherByInstitutionIdAndId(institutionId,id);
        if(teacher == null) {
            throw new EntityNotFoundException(Teacher.class, "id", Long.toString(id));
        }else {
            logger.info("Checking the teacher is a class teacher " + teacher.get().getClassTeacher());
            if(Boolean.parseBoolean(teacher.get().getClassTeacher())== true) {
                logger.info("Finding the class of the class teacher");
                Grade grade = gradeRepository.findGradeByInstitutionIdAndTeacherId(institutionId, id);
                grade.setTeacher("");
                gradeRepository.save(grade);
            }
            teacherRepository.deleteByInstitutionIdAndId(institutionId, id);
        }
    }

    @Override
    public Teacher getTeacherDetails(long institutionId, String email) {
        return teacherRepository.findTeacherByInstitutionIdAndEmail(institutionId, email);
    }


    public ArrayList<Map<String, ArrayList<String>>> generateTimeTable() {
        String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday", "Saturday"};
        ArrayList<Map<String, ArrayList<String>>> timeTable = new ArrayList<>();
        for(String day : days) {
            Map<String, ArrayList<String>> dayTimeTable = new TreeMap<>();
            ArrayList<String> grades = new ArrayList<>();
            for(int i=0; i<10; i++) {
                grades.add("");
            }
            dayTimeTable.put(day, grades);
            timeTable.add(dayTimeTable);
        }

        return timeTable;
    }

    @Async
    public void updateTeacherTimeTable(TimeTable timeTable, long institutionId, String grade, String section) {
        ArrayList<Map<String, ArrayList<Map<String, String>>>> timetable = timeTable.getTimetable();

        for(Map<String, ArrayList<Map<String, String>>> daytimetabe : timetable) {
            Set<String> daykeyset = daytimetabe.keySet();
            Iterator dataSetIterator = daykeyset.iterator();
            String day = (String) dataSetIterator.next();
            ArrayList<Map<String, String>> dayTimeTable = daytimetabe.get(day);
            int index = 0;
            for(Map<String, String> subjectTeacher :dayTimeTable) {
                Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByInstitutionIdAndName(institutionId, subjectTeacher.get("teacher"));
                Teacher teacher = optionalTeacher.get();
                ArrayList<Map<String, ArrayList<String>>> teacherTimeTable = teacher.getTimeTable();
                long teacherId = teacher.getId();
                Map<String, ArrayList<String>> teacherDayTimeTableMap = teacherTimeTable.get(getDayIndex(day));
                ArrayList<String> teacherDayTimeTable = teacherDayTimeTableMap.get(day);
                teacherDayTimeTable.set(index, grade + " " +section);
                teacherDayTimeTableMap.put(day, teacherDayTimeTable);
                teacherTimeTable.set(getDayIndex(day), teacherDayTimeTableMap);
                teacher.setTimeTable(teacherTimeTable);
                teacher.setPersisted(true);
                teacher.setId(teacherId);
                teacherRepository.save(teacher);
                index++;
            }
            index = 0;

        }

    }

    @Override
    public void updateTeacherTimeTableOnDelete(long institutionId, String name, String day, String period) {
        Optional<Teacher> teacherOptional = teacherRepository.findTeacherByInstitutionIdAndName(institutionId, name);
        if(teacherOptional.get() !=null) {
            Teacher teacher = teacherOptional.get();
            ArrayList<Map<String, ArrayList<String>>> teacherTimeTable = teacher.getTimeTable();
            long teacherId = teacher.getId();
            Map<String, ArrayList<String>> teacherDayTimeTableMap = teacherTimeTable.get(getDayIndex(day));
            ArrayList<String> teacherDayTimeTable = teacherDayTimeTableMap.get(day);
            teacherDayTimeTable.set(Integer.parseInt(period), "");
            teacherDayTimeTableMap.put(day, teacherDayTimeTable);
            teacherTimeTable.set(getDayIndex(day), teacherDayTimeTableMap);
            teacher.setTimeTable(teacherTimeTable);
            teacher.setPersisted(true);
            teacher.setId(teacherId);
            teacherRepository.save(teacher);
        }
    }

    private int getDayIndex(String day) {
        List<String> days = new ArrayList(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));
        return days.indexOf(day);
    }
}
