# PctoRist
2° progetto di pcto 2022 4A INF

### Cosa fa questa app
- Questa app permette di selezionare il numero di prodotti da un menu di un ristorante, effettuare un ordine e visualizzare lo storico degli ordini effettuati.

- Prima di effettuare un ordine è richiesta la registrazione di un account per poter associare ad esso gli ordini effettuati.

- L'autenticazione e la gestione degli account (registrazione, login e recupero password) è stata implementata utilizzando il servizio Authentication di Firebase.

- Il servizio Firestore di Firebase fornisce un database NoSQL per permettere la gestione dei dati: 
ogni ordine viene salvato in un documento appartenente ad una collezione associata e soltanto accessibile (grazie alla gestione delle regole di Firestore) all'account loggato. 

- La sezione `Storico ordini` permette di visualizzare in *tempo reale* eventuali modifiche effettuate agli ordini nel database Firestore.

### Requisiti per il funzionamento
- Creare un progetto su Firebase con funzionalità Authentication e Firestore attivate.
- Scaricare il file `google-services.json` e metterlo nella cartella `app/` di questa app.

## Ulteriori miglioramenti possibili
- [ ] Possibilità di eliminare il proprio account dall'app
- [ ] Possibilità di annullare il proprio ordine da una voce del menu per ogni card ordine
- [ ] 
