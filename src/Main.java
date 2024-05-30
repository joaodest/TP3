
    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Date;
    import java.util.List;
    import java.util.Scanner;
    import java.util.UUID;

    public class Main {
        public static void main(String[] args) throws Exception {
            Scanner sc = new Scanner(System.in);
            Data data = new Data();

            String caminhoVerde = "/tmp/characters.csv";
            String caminhoPc = "C:/Users/joaod/Downloads/characters.csv";
            data.lerCsv(caminhoPc);

            //Q01 q01 = new Q01(data);
            //q01.questao01(sc);

            Q02 q02 = new Q02(data);
            q02.questao02(sc);



            sc.close();
        }

        public static boolean isFim(String s) {
            return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
        }

    }

    class Q01 {
        Data data;

        public Q01(Data data){
            this.data = data;
        }

        public void questao01(Scanner sc) {
            while (true) {
                String input = sc.next();
                if (Main.isFim(input))
                    break;

                Personagem personagem = data.getPersonagemById(input);
            }

            int tam = sc.nextInt();
            sc.nextLine();

            ListaSequencial list = new ListaSequencial(tam);

            for (int i = 0; i < tam; i++) {
                if (!sc.hasNextLine()) break;
                String operacao = sc.nextLine();
                operaLista(list, operacao);
            }

            list.mostrar();
        }

        private void operaLista(ListaSequencial list, String operacao) {
            String[] parts = operacao.split(" ");
            String comando = parts[0];
            Personagem p;
            int pos;

            switch (comando) {
                case "II":
                    p = data.getPersonagemById(parts[1]);
                    list.insereInicio(p);
                    break;
                case "I*":
                    pos = Integer.parseInt(parts[1]);
                    p = data.getPersonagemById(parts[2]);
                    list.inserePos(p, pos);
                    break;
                case "IF":
                    p = data.getPersonagemById(parts[1]);
                    list.insereFim(p);
                    break;
                case "RI":
                    p = list.removeInicio();
                    if (p != null) {
                        System.out.println("(R) " + p.getPersonagemName());
                    }
                    break;
                case "R*":
                    pos = Integer.parseInt(parts[1]);
                    p = list.removePos(pos);
                    if (p != null) {
                        System.out.println("(R) " + p.getPersonagemName());
                    }
                    break;
                case "RF":
                    p = list.removeFim();
                    if (p != null) {
                        System.out.println("(R) " + p.getPersonagemName());
                    }
                    break;
            }
        }
    }

    class Q02 {
        Data data;
        List<Personagem> removidos = new ArrayList<>();
        public Q02(Data data){
            this.data = data;
        }

        public void questao02(Scanner sc) throws Exception {

            while (true) {
                String input = sc.next();
                if (Main.isFim(input))
                    break;

                Personagem personagem = data.getPersonagemById(input);
            }

            int tam = sc.nextInt();
            sc.nextLine();

            PilhaSequencial pilha = new PilhaSequencial(tam);

            while (sc.hasNextLine()) {
                String operacao = sc.nextLine();
                operaPilha(pilha, operacao);
            }

            removidos.forEach(p -> System.out.println("(R) " + p.getPersonagemName()));

            System.out.println("[ Top ]");
            pilha.mostrar();
            System.out.println("[ Bottom ]");
        }

        private void operaPilha(PilhaSequencial pilha, String operacao) throws Exception {
            String[] parts = operacao.split(" ");
            String comando = parts[0];
            Personagem p;
            int pos;

            switch (comando) {
                case "I":
                    p = data.getPersonagemById(parts[1]);
                    pilha.empilhar(p);
                    break;

                case "R":
                    p = pilha.desempilhar();
                    removidos.add(p);
                    if (p != null) {
                        System.out.println("(R) " + p.getPersonagemName());
                    }
                    break;
            }
        }
    }

    class Q03 {
        Data data;

        public Q03(Data data){
            this.data = data;
        }

        public void questao03(Scanner sc) {
            while (true) {
                String input = sc.next();
                if (Main.isFim(input))
                    break;

                Personagem personagem = data.getPersonagemById(input);
            }

            int tam = sc.nextInt();
            sc.nextLine();

            ListaFlexivel list = new ListaFlexivel();

            for (int i = 0; i < tam; i++) {
                if (!sc.hasNextLine()) break;
                String operacao = sc.nextLine();
                operaLista(list, operacao);
            }

            list.mostrar();
        }

        private void operaLista(ListaFlexivel list, String operacao) {
            String[] parts = operacao.split(" ");
            String comando = parts[0];
            Personagem p;
            int pos;

            switch (comando) {
                case "II":
                    p = data.getPersonagemById(parts[1]);
                    list.inserirInicio(p);
                    break;
                case "I*":
                    pos = Integer.parseInt(parts[1]);
                    p = data.getPersonagemById(parts[2]);
                    list.inserePos(p, pos);
                    break;
                case "IF":
                    p = data.getPersonagemById(parts[1]);
                    list.inserirFim(p);
                    break;
                case "RI":
                    p = list.removerInicio();
                    if (p != null) {
                        System.out.println("(R) " + p.getPersonagemName());
                    }
                    break;
                case "R*":
                    pos = Integer.parseInt(parts[1]);
                    p = list.removerPos(pos);
                    if (p != null) {
                        System.out.println("(R) " + p.getPersonagemName());
                    }
                    break;
                case "RF":
                    p = list.removeFim();
                    if (p != null) {
                        System.out.println("(R) " + p.getPersonagemName());
                    }
                    break;
            }
        }
    }
    class PilhaSequencial {
        private Personagem[] personagens;
        private int topo;

        public PilhaSequencial(int tamanho){
            this.personagens = new Personagem[tamanho];
            this.topo = -1;
        }

        void empilhar(Personagem p) throws Exception {
            if (topo + 1 >= personagens.length) {
                throw new Exception("Pilha cheia");
            }
            personagens[++topo] = p;
        }

        Personagem desempilhar() throws Exception {
            if (topo == -1) {
                throw new Exception("Pilha vazia");
            }
            Personagem p = personagens[topo];
            personagens[topo--] = null;
            return p;
        }

        void mostrar(){
            for(int i = topo; i >= 0; i--){
                mostrar(i);
            }
        }

        void mostrar(int topo){
            System.out.println(personagens[topo]);
        }
    }

    class ListaFlexivel {
        Personagem primeiro, ultimo;

        public ListaFlexivel() {
            this.primeiro = new Personagem();
            this.ultimo = primeiro;
            this.ultimo.prox = null;
        }

        public void inserirInicio(Personagem p) {
            Personagem temp = p;
            temp.prox = primeiro.prox;
            primeiro.prox = temp;

            if (primeiro == ultimo) {
                ultimo = temp;
                ultimo.prox = null;
            }
        }

        public void inserirFim(Personagem p) {
            ultimo.prox = p;
            ultimo = p;
            ultimo.prox = null;
        }

        public void inserePos(Personagem p, int pos) {
            int len = len();
            if (pos < 0 || pos > len) {
                throw new IndexOutOfBoundsException("erro na posicao: " + pos);
            }
            if (pos == 0) {
                inserirInicio(p);
            } else if (pos == len) {
                inserirFim(p);
            } else {
                Personagem i = primeiro;
                for (int j = 0; j < pos - 1; j++) {
                    i = i.prox;
                }
                Personagem temp = p;
                temp.prox = i.prox;
                i.prox = temp;
            }
        }

        public Personagem removerInicio() {
            if (primeiro.prox == null) {
                return null;
            }
            Personagem temp = primeiro.prox;
            primeiro.prox = temp.prox;
            if (temp == ultimo) {
                ultimo = primeiro;
            }
            return temp;
        }

        public Personagem removeFim() {
            if (primeiro == ultimo) {
                return null;
            }
            Personagem i = primeiro;
            while (i.prox != ultimo) {
                i = i.prox;
            }
            Personagem temp = ultimo;
            ultimo = i;
            ultimo.prox = null;
            return temp;
        }

        public Personagem removerPos(int pos) {
            int len = len();
            if (pos < 0 || pos >= len) {
                throw new IndexOutOfBoundsException("erro: " + pos);
            }
            if (pos == 0) {
                return removerInicio();
            } else if (pos == len - 1) {
                return removeFim();
            } else {
                Personagem i = primeiro;
                for (int j = 0; j < pos - 1; j++) {
                    i = i.prox;
                }
                Personagem temp = i.prox;
                i.prox = temp.prox;
                return temp;
            }
        }

        public int len() {
            int count = 0;
            Personagem tmp = primeiro.prox;
            while (tmp != null) {
                count++;
                tmp = tmp.prox;
            }
            return count;
        }

        public void mostrar() {
            Personagem temp = primeiro.prox;
            while (temp != null) {
                System.out.println(temp);
                temp = temp.prox;
            }
        }
    }

    class ListaSequencial {
        Personagem[] personagens;

        public ListaSequencial(int tamanho){
            this.personagens = new Personagem[tamanho];
        }

        void insereInicio(Personagem p) {
            for(int i = personagens.length - 1; i > 0 ; i--){
                personagens[i] = personagens[i - 1];
            }
            personagens[0] = p;
        }

        void inserePos(Personagem p, int pos){
            for(int i = 0; i < pos; i++){
                if(i == pos - 1){
                    personagens[pos + 1] = personagens[i + 1];
                }
            }
            personagens[pos] = p;
        }

        void insereFim(Personagem p){
            personagens[personagens.length - 1] = p;
        }

        Personagem removeInicio(){
            Personagem p =  personagens[0];
            for(int i = 0; i < personagens.length - 1; i++){
                personagens[i] = personagens[i + 1];
            }

            personagens[personagens.length - 1] = null;

            return p;
        }

        Personagem removePos(int pos){
            Personagem p = personagens[pos];
            for(int i = 0; i < personagens.length - 1; i++){
               if(i >= pos){
                    personagens[i] = personagens[i + 1];
                }
            }

            personagens[personagens.length - 1] = null;
            return p;
        }

        Personagem removeFim(){
            return personagens[personagens.length - 1];
        }

        void mostrar(){
            for(Personagem p : personagens){
                if(p != null){
                    System.out.println(p.toString());
                }
            }
        }
    }


    class Data {
        private List<Personagem> personagens = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        public Data() {
            super();
            this.personagens = new ArrayList<>();
        }

        public List<Personagem> getPersonagens() {
            return personagens;
        }

        public Personagem getPersonagemById(String idString) {
            UUID id = UUID.fromString(idString);

            for (Personagem p : personagens) {
                if (p.getId().equals(id)) {
                    return p;
                }
            }
            return null;
        }

        public void lerCsv(String path) {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String linha;

                br.readLine();
                while ((linha = br.readLine()) != null) {

                    try {
                        Personagem p = instanciaPersonagemPeloCsv(linha);
                        personagens.add(p);
                    } catch (Exception e) {
                        System.err.println("Erro ao processar a linha: " + linha);
                        e.printStackTrace();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Personagem instanciaPersonagemPeloCsv(String linha) throws ParseException {
            String[] values = splitString(linha, ';');
            UUID id = UUID.fromString(values[0]);
            String name = values[1];

            String alternateNamesRaw = replaceAllSimple(values[2], "[", "");
            alternateNamesRaw = replaceAllSimple(alternateNamesRaw, "]", "");
            alternateNamesRaw = replaceAllSimple(alternateNamesRaw, "\"", "");

            ArrayList<String> alternateNames = new ArrayList<>(Arrays.asList(splitString(alternateNamesRaw, ',')));

            String house = values[3];
            String ancestry = values[4];
            String species = values[5];
            String patronus = values[6];
            boolean hogwartsStaff = values[7].equals("VERDADEIRO");
            String hogwartsStudent = values[8];
            String actorName = values[9];
            boolean alive = values[10].equalsIgnoreCase("VERDADEIRO");
            Date dateOfBirth = values[12].isEmpty() ? null : sdf.parse(values[12]);
            int yearOfBirth = Integer.parseInt(values[13]);
            String eyeColour = values[14];
            String gender = values[15];
            String hairColour = values[16];
            boolean wizard = values[17].equalsIgnoreCase("VERDADEIRO");

            return new Personagem(id.toString(), name, alternateNames, house, ancestry, species, patronus, hogwartsStaff,
                    hogwartsStudent, actorName, alive, dateOfBirth, yearOfBirth, eyeColour, gender, hairColour, wizard);
        }

        public static String[] splitString(String input, char delimiter) {
            List<String> result = new ArrayList<>();
            StringBuilder buffer = new StringBuilder();

            for (char ch : input.toCharArray()) {
                if (ch == delimiter) {
                    result.add(buffer.toString());
                    buffer = new StringBuilder();
                } else {
                    buffer.append(ch);
                }
            }
            if (buffer.length() > 0) {
                result.add(buffer.toString());
            }
            return result.toArray(new String[0]);
        }

        public static String replaceAllSimple(String input, String toReplace, String replacement) {
            StringBuilder result = new StringBuilder();
            int start = 0;
            int end = input.indexOf(toReplace);
            while (end != -1) {
                result.append(input, start, end).append(replacement);
                start = end + toReplace.length();
                end = input.indexOf(toReplace, start);
            }
            result.append(input.substring(start));
            return result.toString();
        }

        public static int compareStrings(String s1, String s2) {
            int length1 = s1.length();
            int length2 = s2.length();
            int minLen = Math.min(length1, length2);

            for (int i = 0; i < minLen; i++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);

                if (c1 != c2) {
                    return c1 - c2;
                }
            }

            return length1 - length2;
        }

        public static void printSortedList(ArrayList<Personagem> personagens) {
            for (Personagem p : personagens) {
                System.out.println(p);
            }

        }
    }

    class Personagem {

        private UUID _id;
        private int _yearOfBirth;
        private String _name;
        private ArrayList<String> _alternateNames;
        private String _house;
        private String _ancestry;
        private String _species;
        private String _patronus;
        private String _hogwartsStudent;
        private String _actorName;
        private String _eyeColour;
        private String _gender;
        private String _hairColour;
        private Date _dateOfBirth;
        private boolean _hogwartsStaff;
        private boolean _alive;
        private boolean _wizard;
        public Personagem prox;
        public Personagem() { }

        public Personagem(String id, String name, ArrayList<String> alternateNames, String house, String ancestry,
                          String species, String patronus, boolean hogwartsStaff, String hogwartsStudent, String actorName,
                          boolean alive, Date dateOfBirth, int yearOfBirth, String eyeColour, String gender, String hairColour,
                          boolean wizard) {

            _id = UUID.fromString(id);
            _yearOfBirth = yearOfBirth;
            _name = name;
            _alternateNames = alternateNames;
            _house = house;
            _ancestry = ancestry;
            _species = species;
            _patronus = patronus;
            _hogwartsStudent = hogwartsStudent;
            _actorName = actorName;
            _eyeColour = eyeColour;
            _gender = gender;
            _hairColour = hairColour;
            _dateOfBirth = dateOfBirth;
            _hogwartsStaff = hogwartsStaff;
            _alive = alive;
            _wizard = wizard;
            this.prox = null;
        }

        public static Personagem getPersonagem() {
            return new Personagem();
        }

        public UUID getId() {
            return _id;
        }

        public String getPersonagemName() {
            return _name;
        }

        public String getPersonagemDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(_dateOfBirth);
        }

        public String getHairColour() {
            return _hairColour;
        }

        public int getYearOfBirth() {
            return _yearOfBirth;
        }

        public String getActorName() {
            return _actorName;
        }

        public String getHouse() {
            return _house;
        }

        public String toString(int index) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = (_dateOfBirth != null) ? sdf.format(_dateOfBirth) : "";

            String alternateNamesFormatted = Data.replaceAllSimple(
                    Data.replaceAllSimple(Data.replaceAllSimple(this._alternateNames.toString(), "[", ""), "]", ""), "'",
                    "");

            return String.format(
                    "[%d ## %s ## %s ## {%s} ## %s ## %s ## %s ## %s ## %b ## %b ## %s ## %b ## %s ## %d ## %s ## %s ## %s ## %b]",
                    index, this._id, this._name, alternateNamesFormatted, this._house, this._ancestry, this._species,
                    this._patronus, this._hogwartsStaff, this._hogwartsStudent.equalsIgnoreCase("VERDADEIRO"),
                    this._actorName, this._alive, formattedDate, this._yearOfBirth, this._eyeColour, this._gender,
                    this._hairColour, this._wizard);
        }
    }