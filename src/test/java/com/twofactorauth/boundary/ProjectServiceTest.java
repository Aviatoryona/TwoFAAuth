package com.twofactorauth.boundary;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.twofactorauth.entity.Project;


@RunWith(Arquillian.class)
public class ProjectServiceTest {
  @Deployment
  public static JavaArchive createDeployment() throws IOException {
    final Properties properties = new Properties();
    properties.load(ProjectServiceTest.class.getClassLoader().getResourceAsStream("project.properties"));
    JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addPackages(true, properties.getProperty("groupId"))
            .addAsResource("META-INF/persistence.xml")
            .addAsResource("META-INF/beans.xml");
    System.out.println(jar.toString());
    return jar;
  }

  @Inject
  ProjectService projectService;

  @Test
  public void shouldPersistEntities() throws Exception {
    assertEquals(0, projectService.findAll().size());
    projectService.create(new Project("project A"));
    projectService.create(new Project("project B"));
    projectService.create(new Project("project C"));
    projectService.create(new Project("project D"));
    projectService.create(new Project("project E"));
    List<Project> projects = projectService.findAll();
    assertEquals(5, projects.size());
    System.out.println(projects);
    for (Project project : projects) {
      System.out.printf("movie=%s", project);

    }

    System.out.println(projects);
    assertEquals(5, projects.size());
  }
}
