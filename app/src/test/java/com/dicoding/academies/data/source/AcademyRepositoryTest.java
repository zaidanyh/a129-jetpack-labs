package com.dicoding.academies.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.dicoding.academies.data.source.local.entity.CourseEntity;
import com.dicoding.academies.data.source.local.entity.ModuleEntity;
import com.dicoding.academies.data.source.remote.RemoteRepository;
import com.dicoding.academies.data.source.remote.response.ContentResponse;
import com.dicoding.academies.data.source.remote.response.CourseResponse;
import com.dicoding.academies.data.source.remote.response.ModuleResponse;
import com.dicoding.academies.utils.FakeDataDummy;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcademyRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RemoteRepository remote = mock(RemoteRepository.class);
    private FakeAcademyRepository academyRepository = new FakeAcademyRepository(remote);

    private ArrayList<CourseResponse> courseResponses = FakeDataDummy.generateRemoteDummyCourses();
    private String courseId = courseResponses.get(0).getId();
    private ArrayList<ModuleResponse> moduleResponses = FakeDataDummy.generateRemoteDummyModules(courseId);
    private String moduleId = moduleResponses.get(0).getModuleId();
    private ContentResponse content = FakeDataDummy.generateRemoteDummyContent(moduleId);

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void getAllCourses() {
        when(remote.getAllCourses()).thenReturn(courseResponses);
        ArrayList<CourseEntity> courseEntities = academyRepository.getAllCourses();
        verify(remote).getAllCourses();
        assertNotNull(courseEntities);
        assertEquals(courseResponses.size(), courseEntities.size());
    }

    @Test
    public void getAllModulesByCourse() {
        when(remote.getModules(courseId)).thenReturn(moduleResponses);
        ArrayList<ModuleEntity> moduleEntities = academyRepository.getAllModulesByCourse(courseId);
        verify(remote).getModules(courseId);
        assertNotNull(moduleEntities);
        assertEquals(moduleResponses.size(), moduleEntities.size());
    }

    @Test
    public void getBookmarkedCourses() {
        when(remote.getAllCourses()).thenReturn(courseResponses);
        ArrayList<CourseEntity> courseEntities = academyRepository.getBookmarkedCourses();
        verify(remote).getAllCourses();
        assertNotNull(courseEntities);
        assertEquals(courseResponses.size(), courseEntities.size());
    }

    @Test
    public void getContent() {
        when(remote.getModules(courseId)).thenReturn(moduleResponses);
        when(remote.getContent(moduleId)).thenReturn(content);
        ModuleEntity resultModule = academyRepository.getContent(courseId, moduleId);
        verify(remote).getContent(moduleId);
        assertNotNull(resultModule);
        assertEquals(content.getContent(), resultModule.contentEntity.getContent());
    }


    @Test
    public void getCourseWithModules() {
        when(remote.getAllCourses()).thenReturn(courseResponses);
        CourseEntity resultCourse = academyRepository.getCourseWithModules(courseId);
        verify(remote).getAllCourses();
        assertNotNull(resultCourse);
        assertEquals(courseResponses.get(0).getTitle(), resultCourse.getTitle());
    }
}