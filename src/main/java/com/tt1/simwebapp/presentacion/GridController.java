package com.tt1.simwebapp.presentacion;

import com.tt1.simwebapp.logica.InterfazContactoSim;
import com.tt1.simwebapp.modelo.DatosSimulacion;
import com.tt1.simwebapp.modelo.Punto;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona cómo se muestran los resultados de las simulaciones usando Java Spring. Además, registra las solicitudes
 * realizadas en un logger.
 */
@Controller
public class GridController
{
    private final InterfazContactoSim ics;
    private final Logger logger;

    /**
     * Crea un gestor de resultados de simulaciones con la conexión al simulador y logger pasados cómo parámetros.
     *
     * @param ics    gestor de las comunicaciones (conexión) con el simulador.
     * @param logger registrador de las llamadas realizadas y los errores.
     */
    public GridController(InterfazContactoSim ics, Logger logger)
    {
        this.ics = ics;
        this.logger = logger;
    }

    /**
     * Gestiona las solicitudes GET realizadas al servidor Java Spring en la dirección /grid. Añade al modelo de Java
     * Spring los atributos de anchura de tablero, los segundos máximos y los colores (con sus coordenadas temporales
     * y espaciales) y después devuelve el nombre de la plantilla html que se debe mostrar al usuario para ver el
     * resultado de la simulación.
     *
     * @param tok   el token recibido cómo parámetro en la solitud (/grid?tok=[tok])
     * @param model el modelo de Java Spring
     * @return el nombre de la plantilla html que muestra el grid al usuario.
     */
    @GetMapping("/grid")
    public String solicitud(@RequestParam int tok, Model model)
    {
        DatosSimulacion ds = ics.descargarDatos(tok);
        model.addAttribute("count", ds.getAnchoTablero());
        model.addAttribute("maxTime", ds.getMaxSegundos());
        Map<String, String> colors = new HashMap<>();

        for (var t = 0; t <= ds.getMaxSegundos(); t++)
        {
            for (Punto p : ds.getPuntos().get(t))
            {
                colors.put(t + "-" + p.getY() + "-" + p.getX(), p.getColor());
            }
        }
        model.addAttribute("colors", colors);

        return "grid";
    }
}
