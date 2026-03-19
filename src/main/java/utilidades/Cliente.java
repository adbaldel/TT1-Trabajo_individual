package utilidades;

import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.ResultadosApi;
import org.openapitools.client.api.SolicitudApi;
import org.openapitools.client.model.*;
import org.openapitools.client.api.EmailApi;

import java.util.List;

public class Cliente
{
    public static void main(String[] args)
    {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080");

        // TEST ENVIAR UN MAIL
        String emailAddress = "emailAddress_example";
        String message = "message_example";
        EmailResponse emailResponse = emailPost(defaultClient, emailAddress, message);
        System.out.println(emailResponse);

        // TEST SOLICITAR UNA SIMULACIÓN
        String nombreUsuario = "nombreUsuario_example";
        Solicitud solicitud = new Solicitud(); // Habría que añadirle los datos de entidades y cantidades (creo)
        // Setters de solicitud ...
        SolicitudResponse solicitudResponse = solicitudSolicitarPost(defaultClient, nombreUsuario, solicitud);
        System.out.println(solicitudResponse);

        List<Integer> tokensSolicitudesUsuario = solicitudGetSolicitudesUsuarioGet(defaultClient, nombreUsuario);
        System.out.println(tokensSolicitudesUsuario);

        Integer tok = solicitudResponse.getTokenSolicitud();
        ResultsResponse resultsResponse = resultadosPost(defaultClient, nombreUsuario, tok);
        System.out.println(resultsResponse);
        List<Integer> comprobanteSolicitud = solicitudComprobarSolicitudGet(defaultClient, nombreUsuario, tok);
        System.out.println(comprobanteSolicitud);
    }

    private static EmailResponse emailPost(ApiClient defaultClient, String emailAddress, String message)
    {
        EmailApi apiInstance = new EmailApi(defaultClient);
        EmailResponse result = null;

        try
        {
            result = apiInstance.emailPost(emailAddress, message);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling EmailApi#emailPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }

    private static ResultsResponse resultadosPost(ApiClient defaultClient, String nombreUsuario, Integer tok)
    {
        ResultadosApi apiInstance = new ResultadosApi(defaultClient);
        System.out.println(apiInstance.getCustomBaseUrl());
        ResultsResponse result = null;

        try
        {
            result = apiInstance.resultadosPost(nombreUsuario, tok);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling ResultadosApi#resultadosPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }

    private static List<Integer> solicitudComprobarSolicitudGet(ApiClient defaultClient, String nombreUsuario, Integer tok)
    {
        SolicitudApi apiInstance = new SolicitudApi(defaultClient);
        System.out.println(apiInstance.getCustomBaseUrl());
        List<Integer> result = null;

        try
        {
            result = apiInstance.solicitudComprobarSolicitudGet(nombreUsuario, tok);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling SolicitudApi#solicitudComprobarSolicitudGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }

    private static List<Integer> solicitudGetSolicitudesUsuarioGet(ApiClient defaultClient, String nombreUsuario)
    {
        SolicitudApi apiInstance = new SolicitudApi(defaultClient);
        System.out.println(apiInstance.getCustomBaseUrl());
        List<Integer> result = null;

        try
        {
            result = apiInstance.solicitudGetSolicitudesUsuarioGet(nombreUsuario);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling SolicitudApi#solicitudGetSolicitudesUsuarioGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }

    private static SolicitudResponse solicitudSolicitarPost(ApiClient defaultClient, String nombreUsuario, Solicitud solicitud)
    {
        SolicitudApi apiInstance = new SolicitudApi(defaultClient);
        System.out.println(apiInstance.getCustomBaseUrl());
        SolicitudResponse result = null;

        try
        {
            result = apiInstance.solicitudSolicitarPost(nombreUsuario, solicitud);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling SolicitudApi#solicitudSolicitarPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }
}
