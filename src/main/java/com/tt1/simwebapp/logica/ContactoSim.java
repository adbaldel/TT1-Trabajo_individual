package com.tt1.simwebapp.logica;

import com.tt1.simwebapp.modelo.DatosSimulacion;
import com.tt1.simwebapp.modelo.DatosSolicitud;
import com.tt1.simwebapp.modelo.Entidad;
import com.tt1.simwebapp.modelo.Punto;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.ResultadosApi;
import org.openapitools.client.api.SolicitudApi;
import org.openapitools.client.model.ResultsResponse;
import org.openapitools.client.model.Solicitud;
import org.openapitools.client.model.SolicitudResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestor de la conexión con el servidor de simulaciones usando las utilidades generadas con el OpenAPI Generator a
 * partir de la especificación OpenAPI del servidor dado (swagger.json).
 */
@Service
public class ContactoSim implements InterfazContactoSim
{
    private static final String LOCALHOST_SIM = "http://localhost:8080";
    private static final String DOCKERCOMPOSE_SIM = "http://servicio-tt1:8080";

    private final String nombreUsuario;
    private final Map<Integer, Entidad> entidades;
    private final ApiClient client;

    /**
     * Crea un nuevo gestor de comunicaciones con el servidor de simulaciones con las entidades:
     * - id: 1, nombre: gatos, descripción: Gatos salvajes
     * - id: 2, nombre: perro, descripción: Perros domésticos
     * , el cliente de servidor de simulaciones por defecto conectado a http://localhost:8080 y el nombre de usuario
     * "trabajo-individual-tt1".
     */
    public ContactoSim()
    {
        entidades = new HashMap<>();

        Entidad entidad = new Entidad();
        entidad.setId(1);
        entidad.setName("gato");
        entidad.setDescripcion("Gatos salvajes");
        entidades.put(entidad.getId(), entidad);

        entidad = new Entidad();
        entidad.setId(2);
        entidad.setName("perros");
        entidad.setDescripcion("Perros domésticos");
        entidades.put(entidad.getId(), entidad);

        nombreUsuario = "trabajo-individual-tt1";

        client = Configuration.getDefaultApiClient();
        client.setBasePath(DOCKERCOMPOSE_SIM);
    }

    /**
     * Crea un nuevo gestor de comunicaciones con el servidor de simulaciones con las entidades, nombre de usuario y
     * cliente de servidor de simulaciones pasados como parámetros. El mapa de entidades tiene como clave el id de la
     * entidad y como valor el objeto entidad correspondiente.
     *
     * @param entidades     el mapa de entidades con clave el id de la entidad, valor la entidad correspondiente.
     * @param nombreUsuario el nombre de usuario a usar.
     * @param client        el cliente del servidor de simulaciones.
     */
    public ContactoSim(Map<Integer, Entidad> entidades, String nombreUsuario, ApiClient client)
    {
        this.entidades = entidades;
        this.nombreUsuario = nombreUsuario;
        this.client = client;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int solicitarSimulation(DatosSolicitud datosSolicitud)
    {
        int token = -1;
        SolicitudApi apiInstance = new SolicitudApi(client);
        Solicitud solicitud;
        SolicitudResponse result;

        solicitud = datosSolcitudToSolicitud(datosSolicitud);

        try
        {
            result = apiInstance.solicitudSolicitarPost(nombreUsuario, solicitud);
            token = result.getTokenSolicitud();
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling SolicitudApi#solicitudSolicitarPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entidad> getEntities()
    {
        List<Entidad> entidadesList = new ArrayList<>();

        for (Map.Entry<Integer, Entidad> entry : entidades.entrySet())
        {
            entidadesList.add(entry.getValue());
        }

        return entidadesList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidEntityId(int id)
    {
        boolean valid;

        valid = entidades.containsKey(id);

        return valid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatosSimulacion descargarDatos(int tok)
    {
        ResultadosApi apiInstance = new ResultadosApi(client);
        ResultsResponse result;
        DatosSimulacion datosSimulacion = null;

        try
        {
            result = apiInstance.resultadosPost(nombreUsuario, tok);
            datosSimulacion = dataToDatosSimulation(result.getData());
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling ResultadosApi#resultadosPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return datosSimulacion;
    }

    private Solicitud datosSolcitudToSolicitud(DatosSolicitud sol)
    {
        Solicitud solicitud = new Solicitud();
        List<Integer> cantidadesEntidades = new ArrayList<>();
        List<String> nombresEntidades = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : sol.getNums().entrySet())
        {
            cantidadesEntidades.add(entry.getValue());
            nombresEntidades.add(entidades.get(entry.getKey()).getName());
        }
        solicitud.setCantidadesIniciales(cantidadesEntidades);
        solicitud.setNombreEntidades(nombresEntidades);

        return solicitud;
    }

    private static DatosSimulacion dataToDatosSimulation(String data)
    {
        DatosSimulacion datosSimulacion = new DatosSimulacion();
        String[] dataLines;
        Map<Integer, List<Punto>> puntos = new HashMap<>();
        int anchoTablero = -1;
        int maxSegundos = -1;
        String line;
        String[] lineData;
        int sec;
        Punto punto;

        dataLines = data.split("\n");

        anchoTablero = Integer.parseInt(dataLines[0]);
        for (int i = 1; i < dataLines.length; i++)
        {
            line = dataLines[i];
            lineData = line.split(",");

            sec = Integer.parseInt(lineData[0]);

            if (sec > maxSegundos)
            {
                maxSegundos = sec;
            }

            punto = new Punto();
            punto.setX(Integer.parseInt(lineData[1]));
            punto.setY(Integer.parseInt(lineData[2]));
            punto.setColor(lineData[3]);

            if (!puntos.containsKey(sec))
            {
                puntos.put(sec, new ArrayList<>());
            }

            puntos.get(sec).add(punto);
        }

        datosSimulacion.setMaxSegundos(maxSegundos);
        datosSimulacion.setAnchoTablero(anchoTablero);
        datosSimulacion.setPuntos(puntos);

        return datosSimulacion;
    }
}