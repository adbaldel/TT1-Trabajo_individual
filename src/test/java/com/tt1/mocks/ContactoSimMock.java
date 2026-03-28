package com.tt1.mocks;

import interfaces.InterfazContactoSim;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import modelo.Punto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactoSimMock implements InterfazContactoSim
{
    private List<Entidad> entidades;
    private List<String> coloresEntidades;

    private Map<Integer, DatosSolicitud> solicitudes;

    public ContactoSimMock()
    {
        solicitudes = new HashMap<>();

        entidades = new ArrayList<>();

        Entidad entidad = new Entidad();
        entidad.setId(1);
        entidad.setName("gatos");
        entidad.setDescripcion("Gatos salvajes");
        entidades.add(entidad);

        entidad = new Entidad();
        entidad.setId(2);
        entidad.setName("perros");
        entidad.setDescripcion("Perros domésticos");
        entidades.add(entidad);

        coloresEntidades = List.of("red", "green");
    }

    @Override
    public int solicitarSimulation(DatosSolicitud sol)
    {
        int token = 1;

        solicitudes.put(token, sol);

        return token;
    }

    @Override
    public List<Entidad> getEntities()
    {
        return entidades;
    }

    @Override
    public boolean isValidEntityId(int id)
    {
        boolean valid = false;
        int i = 0;

        while (!valid && i < entidades.size())
        {
            valid = (id == entidades.get(i).getId());
            i++;
        }

        return valid;
    }

    @Override
    public DatosSimulation descargarDatos(int ticket)
    {
        DatosSimulation sim = new DatosSimulation();
        int maxSegundos = 10;
        int anchoTablero = 10;
        Map<Integer, List<Punto>> puntos = new HashMap<>();
        List<Punto> puntosSec;
        Punto punto;
        int colorPunto;

        sim.setMaxSegundos(maxSegundos);
        sim.setAnchoTablero(anchoTablero);

        for (int sec = 0; sec < maxSegundos; sec++)
        {
            puntosSec = new ArrayList<>();

            for (int x = 0; x < anchoTablero; x++)
            {
                for (int y = 0; y < anchoTablero; y++)
                {
                    colorPunto = (x + y + sec) % 3;

                    if (colorPunto < 2)
                    {
                        punto = new Punto();
                        punto.setX(x);
                        punto.setY(y);
                        punto.setColor(coloresEntidades.get(colorPunto));

                        puntosSec.add(punto);
                    }
                }
            }

            puntos.put(sec, puntosSec);
        }

        sim.setPuntos(puntos);

        return sim;
    }
}
