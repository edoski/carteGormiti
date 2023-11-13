package carte_gioco;

public enum Impact {
    INCREMENTO, RINASCITA, CONDANNA, CONDIZIONE, RAGGIRO, PROTEZIONE, KAMIKAZE, CONGELAMENT0, NOBILE;
}


/*
Incremento: aumenta le di una carta del abilità del 20%
Rinascita: una carta a scelta tra quelle nel cimitero puo tornare nel campo di battaglia
Condanna: una carta a scelta tra quelle nel campo di battaglia viene distrutta
Condizione: se possiedi 4 carte dello stesso tipo puoi infliggere un attacco simultaneo a 4 carte avversarie
Raggiro: scambia 2 carte a scelta con quelle dell'avversario
Protezione: una carta a scelta tra quelle nel campo di battaglia non puo essere attaccata
KAMIKAZE: Sacrifichi una carta per distruggere 3 carte avversarie
Congelamento: una carta a scelta tra quelle nel campo di battaglia non puo essere utilizzata per 2 turni
Nobile: la carta su cui viene applicato questo effetto puo attaccare 2 volte ogni turno
 */