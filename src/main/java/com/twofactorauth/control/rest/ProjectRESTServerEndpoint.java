package com.twofactorauth.control.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.twofactorauth.entity.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/projects")
@Tag(name = "Project")
public interface ProjectRESTServerEndpoint {

  @GET
  @Path("/item/{id}")
  @Produces(APPLICATION_JSON)
  @Operation(
          summary = "Get project by project id",
          responses = {
                  @ApiResponse(description = "The Project",
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = Project.class))),
                  @ApiResponse(responseCode = "400", description = "Project not found")})
  Response retrieveProject(

          @PathParam("id") long projectId);
  @POST
  @Path("/item")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  Response createProject( Project projectObject );
  @PUT
  @Path("/item/{projectId}")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  Response updateProject(
          @PathParam("projectId") int projectId,
          JsonObject projectObject )
          throws Exception;
  @POST
  @Path("/item/{projectId}/task")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  public String createNewTaskOnProject(
          @PathParam("projectId") int projectId,
          JsonObject taskObject )
          throws Exception;

  @PUT
  @Path("/item/{projectId}/task/{taskId}")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  public String updateTaskOnProject(
          @PathParam("projectId") int projectId,
          @PathParam("taskId") int taskId,
          JsonObject taskObject )
          throws Exception;
  @DELETE
  @Path("/item/{projectId}/task/{taskId}")
  @Consumes( { APPLICATION_JSON, APPLICATION_XML, TEXT_PLAIN })
  @Produces(APPLICATION_JSON)
  public String removeTaskFromProject(
          @PathParam("projectId") int projectId,
          @PathParam("taskId") int taskId,
          JsonObject taskObject )
          throws Exception;
  @GET
  @Path("/item/{projectId}/task/{taskId}/delete")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  public String angularRemoveTaskFromProject(
          @PathParam("projectId") int projectId,
          @PathParam("taskId") int taskId,
          JsonObject taskObject )
          throws Exception;
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/list")
  public void getProjectList(
          @Suspended final AsyncResponse asyncResponse);
}
