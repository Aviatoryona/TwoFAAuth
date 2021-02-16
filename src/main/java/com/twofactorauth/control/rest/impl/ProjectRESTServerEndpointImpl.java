package com.twofactorauth.control.rest.impl;

import java.io.StringWriter;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import com.twofactorauth.boundary.ProjectService;
import com.twofactorauth.control.rest.ProjectRESTServerEndpoint;
import com.twofactorauth.entity.Project;

@Stateless
public class ProjectRESTServerEndpointImpl implements ProjectRESTServerEndpoint {

  static JsonGeneratorFactory jsonGeneratorFactory
          = Json.createGeneratorFactory(
          new HashMap<>() {{
            put(JsonGenerator.PRETTY_PRINTING, true);
          }});

  @EJB
  ProjectService service;

  @Override
  public Response retrieveProject(long projectId) {
    if (projectId < 1)
      throw new RuntimeException(
              "Invalid projectId:["+projectId+"] supplied");

    Project project = service.find( projectId );
    if ( project==null) {
      throw new RuntimeException(
              "No project was found with projectId:["+projectId+"]");
    }

    StringWriter swriter = new StringWriter();
    JsonGenerator generator
            = jsonGeneratorFactory.createGenerator(swriter);
    ProjectHelper.writeProjectAsJson(generator, project).close();
    return Response.ok().entity(swriter.toString()).build();

  }

  @Override
  public Response createProject(Project projectObject) {
    service.create(projectObject);
    StringWriter swriter = new StringWriter();
    JsonGenerator generator =
            jsonGeneratorFactory.createGenerator(swriter);
    ProjectHelper.writeProjectAsJson(generator, projectObject).close();
    return Response.ok().entity( swriter.toString()).build();
  }

  @Override
  public Response updateProject(int projectId, JsonObject projectObject) throws Exception {
    return null;
  }

  @Override
  public String createNewTaskOnProject(int projectId, JsonObject taskObject) throws Exception {
    return null;
  }

  @Override
  public String updateTaskOnProject(int projectId, int taskId, JsonObject taskObject) throws Exception {
    return null;
  }

  @Override
  public String removeTaskFromProject(int projectId, int taskId, JsonObject taskObject) throws Exception {
    return null;
  }

  @Override
  public String angularRemoveTaskFromProject(int projectId, int taskId, JsonObject taskObject) throws Exception {
    return null;
  }

  @Override
  public void getProjectList(AsyncResponse asyncResponse) {

  }
}
