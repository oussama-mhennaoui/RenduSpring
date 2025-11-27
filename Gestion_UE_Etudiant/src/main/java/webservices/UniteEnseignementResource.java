package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/UE")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UniteEnseignementResource {

    private UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();

    // A.1) Création d'une nouvelle unité d'enseignement - POST /UE
    @POST
    public Response createUE(UniteEnseignement ue) {
        try {
            boolean created = ueBusiness.addUniteEnseignement(ue);
            if (created) {
                return Response.status(Response.Status.CREATED).entity(ue).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // A.2) Récupération de la liste de toutes les unités d'enseignements - GET /UE
    @GET
    public Response getAllUEs() {
        try {
            List<UniteEnseignement> ues = ueBusiness.getListeUE();
            return Response.status(Response.Status.OK).entity(ues).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving UEs: " + e.getMessage())
                    .build();
        }
    }

    // A.3) Récupération de la liste des unités d'enseignements d'un semestre spécifique - GET /UE/semestre/{semestre}
    @GET
    @Path("/semestre/{semestre}")
    public Response getUEsBySemestre(@PathParam("semestre") int semestre) {
        try {
            List<UniteEnseignement> ues = ueBusiness.getUEBySemestre(semestre);
            return Response.status(Response.Status.OK).entity(ues).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving UEs by semestre: " + e.getMessage())
                    .build();
        }
    }

    // A.4) Suppression d'une unité d'enseignement - DELETE /UE/{code}
    @DELETE
    @Path("/{code}")
    public Response deleteUE(@PathParam("code") int code) {
        try {
            boolean deleted = ueBusiness.deleteUniteEnseignement(code);
            if (deleted) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("UE not found with code: " + code)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting UE: " + e.getMessage())
                    .build();
        }
    }

    // A.5) Modification d'une unité d'enseignement - PUT /UE/{code}
    @PUT
    @Path("/{code}")
    public Response updateUE(@PathParam("code") int code, UniteEnseignement updatedUE) {
        try {
            updatedUE.setCode(code);
            boolean updated = ueBusiness.updateUniteEnseignement(code, updatedUE);
            if (updated) {
                return Response.status(Response.Status.OK).entity(updatedUE).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("UE not found with code: " + code)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating UE: " + e.getMessage())
                    .build();
        }
    }

    // A.6) Récupération d'une unité d'enseignement ayant un code donné - GET /UE/code/{code}
    @GET
    @Path("/code/{code}")
    public Response getUEByCode(@PathParam("code") int code) {
        try {
            UniteEnseignement ue = ueBusiness.getUEByCode(code);
            if (ue != null) {
                return Response.status(Response.Status.OK).entity(ue).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("UE not found with code: " + code)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving UE: " + e.getMessage())
                    .build();
        }
    }
}