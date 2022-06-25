package agents;

import behaviour.CyclicBehaviourReceiveMessage;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/*
Agente de percepcion de informacion. Recibe datos externos de las estadisticas de NBA que queremos procesar,
en este caso se hace mediante metodos de recuperacion de informacion de una API web
 */
public class DataAgent extends Agent {

    private static final long serialVersionUID = 1L;
    private static final String SERVICE_NAME = "Data Communicator";
    private static final String SERVER_TYPE = "Raw Data Communication";

    /*
    TODO: investigar esta clase (no parece estar en la libreria
    private ChatJFrame charJFrame;
     */
    protected CyclicBehaviourReceiveMessage receiveMessageBehaviour;


    private ArrayList<Producto> listaJugadores = new ArrayList<Jugador>();
    private String tiposFilePath = "jugadores.json";


    public  DataAgent () {
        super();

        /*
        this.charJFrame = new ChatJFrame(this);
         */
        this.receiveMessageBehaviour = new CyclicBehaviourReceiveMessage();
    }

    @Override
    protected void setup() {

        final DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());

        final ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName(SERVICE_NAME);
        serviceDescription.setType(SERVER_TYPE);
        serviceDescription.addLanguages(new SLCodec().getName());

        agentDescription.addServices(serviceDescription);

        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException e) {
            this.loge(e.getMessage());
        }

        /*
        this.getChatJFrame().setVisible(true);
         */
        this.addBehaviour(this.receiveMessageBehaviour);
    }


    public ArrayList<Producto> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(ArrayList<Producto> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public ArrayList<Producto> leemosJugador() throws FileNotFoundException, IOException {
        ArrayList<Jugador> jugadores;
        FileReader jugadoresFile = new FileReader(tiposFilePath);
        Type mapTokenType = new TypeToken<ArrayList<Jugador>>(){}.getType();
        jugadores= new Gson().fromJson(jugadoresFile, mapTokenType);
        jugadoresFile.close();
        return jugadores;
    }


    public void escribimosJugador(ArrayList<Jugador> jugadores) throws IOException {
        FileWriter jugadorFile = new FileWriter(tiposFilePath);
        Gson gson = new Gson();
        gson.toJson(jugadores,jugadorFile);
        jugadorFile.close();
    }


    public void ActualizamosSalario(int nombreJugador, int nuevoSalario, int bonus) throws IOException{
        FileWriter jugadoresFile = new FileWriter(tiposFilePath);
        Gson gson = new Gson();

        if(bonus > 0) {
            gson.toJson(this.listaJugadores,jugadoresFile);
            jugadoresFile.close();
            //aqui hay que actualizar el Producto en "productos.json" con la nueva calificacion y realizar una rebaja en el precio
        }
        else {
            //aqui hay que actualizar el Producto en "productos.json" con la nueva calificacion
            gson.toJson(this.listaJugadores,jugadoresFile);
            jugadoresFile.close();
        }
    }


    @Override
    public void doDelete() {
        super.doDelete();
        loge(": Exit!!!");
    }


    /*
    private void sendMessage(final String text) {
        ACLMessage message = new ACLMessage(ACLMessage.CONFIRM);

        Add receiver agents of the message
        message.addReceiver();

        Add message content (if necessary)
        message.setContent();

        send(message);
    }
    */

    private void log(String s){
        System.out.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }

    private void loge(String s) {
        System.err.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }
}
