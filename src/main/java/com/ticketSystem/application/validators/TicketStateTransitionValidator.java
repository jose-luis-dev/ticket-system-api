package com.ticketSystem.application.validators;

import com.ticketSystem.enums.EstadoOperacional;
import java.util.*;

public class TicketStateTransitionValidator {

    private static final Map<EstadoOperacional, Set<EstadoOperacional>> TRANSICIONES_VALIDAS;

    static {
        Map<EstadoOperacional, Set<EstadoOperacional>> map = new HashMap<>();

        map.put(EstadoOperacional.ABIERTO,
                EnumSet.of(EstadoOperacional.EN_PROCESO, EstadoOperacional.CANCELADO));

        map.put(EstadoOperacional.EN_PROCESO,
                EnumSet.of(EstadoOperacional.CERRADO, EstadoOperacional.CANCELADO));

        // Estados finales - ninguna transición permitida
        map.put(EstadoOperacional.CERRADO,
                EnumSet.noneOf(EstadoOperacional.class));

        map.put(EstadoOperacional.CANCELADO,
                EnumSet.noneOf(EstadoOperacional.class));

        TRANSICIONES_VALIDAS = Collections.unmodifiableMap(map);
    }

    public static boolean esValida(EstadoOperacional actual, EstadoOperacional siguiente) {
        if (actual == null || siguiente == null) return false;

        Set<EstadoOperacional> permitidos = TRANSICIONES_VALIDAS
                .getOrDefault(actual, EnumSet.noneOf(EstadoOperacional.class));

        return permitidos.contains(siguiente);

    }
}
