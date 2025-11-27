package webservices;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/modules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModuleResource {

    private ModuleBusiness moduleBusiness = new ModuleBusiness();
    private UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();

    // B.1) Création d'un nouveau Module - POST /modules
    @POST
    public Response createModule(Module module) {
        try {
            boolean created = moduleBusiness.addModule(module);
            if (created) {
                return Response.status(Response.Status.CREATED).entity(module).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Could not create module. UE might not exist.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error creating module: " + e.getMessage())
                    .build();
        }
    }

    // B.2) Récupération de la liste de tous les modules - GET /modules
    @GET
    public Response getAllModules() {
        try {
            List<Module> modules = moduleBusiness.getAllModules();
            return Response.status(Response.Status.OK).entity(modules).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving modules: " + e.getMessage())
                    .build();
        }
    }

    // B.3) Récupération d'un module ayant un matricule spécifique - GET /modules/{matricule}
    @GET
    @Path("/{matricule}")
    public Response getModuleByMatricule(@PathParam("matricule") String matricule) {
        try {
            Module module = moduleBusiness.getModuleByMatricule(matricule);
            if (module != null) {
                return Response.status(Response.Status.OK).entity(module).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Module not found with matricule: " + matricule)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving module: " + e.getMessage())
                    .build();
        }
    }

    // B.4) Suppression d'un module ayant un matricule spécifique - DELETE /modules/{matricule}
    @DELETE
    @Path("/{matricule}")
    public Response deleteModule(@PathParam("matricule") String matricule) {
        try {
            boolean deleted = moduleBusiness.deleteModule(matricule);
            if (deleted) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Module not found with matricule: " + matricule)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting module: " + e.getMessage())
                    .build();
        }
    }

    // B.5) Modification d'un module ayant un matricule spécifique - PUT /modules/{matricule}
    @PUT
    @Path("/{matricule}")
    public Response updateModule(@PathParam("matricule") String matricule, Module updatedModule) {
        try {
            boolean updated = moduleBusiness.updateModule(matricule, updatedModule);
            if (updated) {
                return Response.status(Response.Status.OK).entity(updatedModule).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Module not found with matricule: " + matricule)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error updating module: " + e.getMessage())
                    .build();
        }
    }

    // B.6) Récupérer tous les modules pédagogiques associés à une UE spécifique - GET /modules/UE/{codeUE}
    @GET
    @Path("/UE/{codeUE}")
    public Response getModulesByUE(@PathParam("codeUE") int codeUE) {
        try {
            UniteEnseignement ue = ueBusiness.getUEByCode(codeUE);
            if (ue == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("UE not found with code: " + codeUE)
                        .build();
            }

            List<Module> modules = moduleBusiness.getModulesByUE(ue);
            return Response.status(Response.Status.OK).entity(modules).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving modules by UE: " + e.getMessage())
                    .build();
        }
    }
}