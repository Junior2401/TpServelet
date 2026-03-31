package fr.istic.taa.jaxrs.tools;

public abstract class tools {
    public enum StatutTicket {
        ACHETE,
        ANNULE,
        REMBOURSE
    }

    public enum StatutEvenement {
        CREE,
        VALIDE,
        ANNULE,
        PLANIFIE
    }
}