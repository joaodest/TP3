
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
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Data data = new Data();

        String caminhoVerde = "/tmp/characters.csv";
        String caminhoPc = "C:/Users/joaod/Downloads/characters.csv";
        data.lerCsv(caminhoVerde);

        //Q01 q01 = new Q01(data);
        //q01.questao01(sc);

        //Q02 q02 = new Q02(data);
        //q02.questao02(sc);

        //Q05 q05 = new Q05(data);
        //q05.questao05(sc);

        Q06 Q06 = new Q06(data);
        Q06.questao06(sc);

        //Q09 q09 = new Q09();
        //q09.questao09(sc);

        //Q11 q11 = new Q11(data);
        //q11.questao11(sc);
        sc.close();
    }

    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

}

class Q01 {
    Data data;
    List<Personagem> removidos = new ArrayList<>();

    public Q01(Data data) {
        this.data = data;
    }

    public void questao01(Scanner sc) {
        ListaSequencial list = new ListaSequencial(data);
        while (true) {
            String input = sc.next();
            if (Main.isFim(input))
                break;

            Personagem personagem = data.getPersonagemById(input);
            list.insereFim(personagem);
        }

        int tam = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < tam; i++) {
            String operacao = sc.nextLine();
            String[] parts = operacao.split(" ");
            String comando = parts[0];
            Personagem p;
            int pos;
            switch (comando) {
                case "II":
                    p = data.getPersonagemById(parts[1]);
                    if (p != null) {
                        list.insereInicio(p);
                    }
                    break;
                case "I*":
                    pos = Integer.parseInt(parts[1]);
                    p = data.getPersonagemById(parts[2]);
                    if (p != null) {
                        list.inserePos(p, pos);
                    }
                    break;
                case "IF":
                    p = data.getPersonagemById(parts[1]);
                    if (p != null) {
                        list.insereFim(p);
                    }
                    break;
                case "RI":
                    p = list.removeInicio();
                    if (p != null) {
                        removidos.add(p);
                    }
                    break;
                case "R*":
                    pos = Integer.parseInt(parts[1]);
                    p = list.removePos(pos);
                    if (p != null) {
                        removidos.add(p);
                    }
                    break;
                case "RF":
                    p = list.removeFim();
                    if (p != null) {
                        removidos.add(p);
                    }
                    break;

            }
        }
        removidos.forEach(p -> System.out.println("(R) " + p.getPersonagemName()));

        list.mostrar();
    }

}

class Q02 {
    Data data;
    List<Personagem> removidos = new ArrayList<>();

    public Q02(Data data) {
        this.data = data;
    }

    public void questao02(Scanner sc) throws Exception {
        PilhaSequencial pilha = new PilhaSequencial(data);
        while (true) {
            String input = sc.next();
            if (Main.isFim(input))
                break;

            Personagem personagem = data.getPersonagemById(input);
            pilha.empilhar(personagem);
        }

        int tam = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < tam; i++) {
            String operacao = sc.nextLine();
            String[] parts = operacao.split(" ");
            String comando = parts[0];
            Personagem p;

            switch (comando) {
                case "I":
                    p = data.getPersonagemById(parts[1]);
                    pilha.empilhar(p);
                    break;

                case "R":
                    p = pilha.desempilhar();
                    removidos.add(p);

                    break;
            }
        }

        removidos.forEach(p -> System.out.println("(R) " + p.getPersonagemName()));
        pilha.mostrar();
    }

}

class Q05 {
    Data data;
    List<Personagem> removidos = new ArrayList<>();

    public Q05(Data data) {
        this.data = data;
    }

    public void questao05(Scanner sc) {
        ListaFlexivel list = new ListaFlexivel();
        while (true) {
            String input = sc.next();
            if (Main.isFim(input))
                break;

            Personagem personagem = data.getPersonagemById(input);
            list.inserirFim(personagem);
        }

        int tam = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < tam; i++) {
            String operacao = sc.nextLine();
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
                    removidos.add(p);
                    break;
                case "R*":
                    pos = Integer.parseInt(parts[1]);
                    p = list.removerPos(pos);
                    removidos.add(p);
                    break;
                case "RF":
                    p = list.removeFim();
                    removidos.add(p);
                    break;
            }
        }

        removidos.forEach(p -> System.out.println("(R) " + p.getPersonagemName()));
        list.mostrar();
    }
}

class Q06 {
    Data data;
    List<Personagem> removidos = new ArrayList<>();

    public Q06(Data data) {
        this.data = data;
    }

    public void questao06(Scanner sc) throws Exception {
        PilhaFlexivel pilha = new PilhaFlexivel();
        while (true) {
            String input = sc.next();

            if (Main.isFim(input))
                break;

            Personagem personagem = data.getPersonagemById(input);
            pilha.empilhar(personagem);
        }

        int tam = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < tam; i++) {
            String operacao = sc.nextLine();
            String[] parts = operacao.split(" ");
            String comando = parts[0];
            Personagem p;

            switch (comando) {
                case "I":
                    p = data.getPersonagemById(parts[1]);
                    pilha.empilhar(p);
                    break;

                case "R":
                    p = pilha.desempilhar();
                    removidos.add(p);
                    break;
            }
        }

        removidos.forEach(p -> System.out.println("(R) " + p.getPersonagemName()));
        pilha.mostrar();
    }
}

class Q09 {

    public void questao09(Scanner sc) throws Exception {
        int numCasos = sc.nextInt();

        for (int caso = 0; caso < numCasos; caso++) {
            int linhas1 = sc.nextInt();
            int colunas1 = sc.nextInt();
            sc.nextLine();

            MatrizDinamica matriz1 = new MatrizDinamica(linhas1, colunas1);
            for (int i = 0; i < linhas1; i++) {
                String[] elementos = sc.nextLine().split(" ");
                matriz1.preencherLinha(i, elementos);
            }

            int linhas2 = sc.nextInt();
            int colunas2 = sc.nextInt();
            sc.nextLine();

            MatrizDinamica matriz2 = new MatrizDinamica(linhas2, colunas2);
            for (int i = 0; i < linhas2; i++) {
                String[] elementos = sc.nextLine().split(" ");
                matriz2.preencherLinha(i, elementos);
            }

            matriz1.mostrarDiagonalPrincipal();
            matriz1.mostrarDiagonalSecundaria();

            if (linhas1 == linhas2 && colunas1 == colunas2) {
                MatrizDinamica soma = matriz1.soma(matriz2);
                soma.mostrar();
            }

            if (colunas1 == linhas2) {
                MatrizDinamica multiplicacao = matriz1.multiplicacao(matriz2);
                multiplicacao.mostrar();
            }
        }
    }
}

class Q11 {
    private Data data;
    private long comparisons = 0;
    private long movements = 0;
    private double startTime = 0;
    private double endTime = 0;

    public Q11(Data data) {
        this.data = data;
    }

    public void questao11(Scanner sc) throws Exception {
        ListaDuplamenteEncadeada lista = new ListaDuplamenteEncadeada();

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (Main.isFim(input))
                break;

            Personagem personagem = data.getPersonagemById(input);
            lista.append(personagem);

        }

        startTime = System.nanoTime();
        lista.quickSort(lista.primeiro, lista.ultimo);
        endTime = System.nanoTime();

        lista.printList();
        writeLog();
    }

    private void writeLog() throws Exception {
        double duration = (endTime - startTime) / 1e6;
        try (PrintWriter log = new PrintWriter(new FileWriter("matricula_quicksort2.txt"))) {
            log.printf("1234567\t%d\t%d\t%.2f\n", comparisons, movements, duration);
        }
    }
}

class ListaDuplamenteEncadeada {
    CelulaPersonagem primeiro, ultimo;
    private long comparisons = 0;
    private long movements = 0;
    private double startTime = 0;
    private double endTime = 0;

    public ListaDuplamenteEncadeada() {
        this.comparisons = 0;
        this.movements = 0;
        this.startTime = 0.0;
        this.endTime = 0.0;
    }

    public void append(Personagem p) {
        CelulaPersonagem newCelula = new CelulaPersonagem(p);
        if (primeiro == null) {
            primeiro = ultimo = newCelula;
        } else {
            ultimo.prox = newCelula;
            newCelula.ant = ultimo;
            ultimo = newCelula;
        }
    }

    public void printList() {
        CelulaPersonagem current = primeiro;
        while (current != null) {
            System.out.println(current.personagem.toString());
            current = current.prox;
        }
    }

    public void quickSort(CelulaPersonagem left, CelulaPersonagem right) {
        if (right != null && left != right && left != right.prox) {
            CelulaPersonagem temp = partition(left, right);
            quickSort(left, temp.ant);
            quickSort(temp.prox, right);
        }
    }

    private CelulaPersonagem partition(CelulaPersonagem left, CelulaPersonagem right) {
        String house = right.personagem.getHouse();
        String name = right.personagem.getPersonagemName();
        CelulaPersonagem i = left.ant;

        for (CelulaPersonagem j = left; j != right; j = j.prox) {
            comparisons++;
            int houseComparison = j.personagem.getHouse().compareTo(house);
            if (houseComparison < 0 || (houseComparison == 0 && j.personagem.getPersonagemName().compareTo(name) < 0)) {
                i = (i == null) ? left : i.prox;
                Personagem temp = i.personagem;
                i.personagem = j.personagem;
                j.personagem = temp;
                movements++;
            }
        }
        i = (i == null) ? left : i.prox;
        Personagem temp = i.personagem;
        i.personagem = right.personagem;
        right.personagem = temp;
        movements++;
        return i;
    }
}

class CelulaPersonagem {
    public Personagem personagem;
    public CelulaPersonagem prox, ant;

    public CelulaPersonagem() {
        this.personagem = null;
        this.prox = this.ant = null;
    }

    public CelulaPersonagem(Personagem personagem) {
        this.personagem = personagem;
        this.prox = this.ant = null;
    }
}

class MatrizDinamica {
    private Celula inicio;
    private int linhas, colunas;

    public MatrizDinamica(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.inicio = new Celula(0);

        Celula temp = inicio;
        for (int j = 1; j < colunas; j++) {
            temp.dir = new Celula(0);
            temp.dir.esq = temp;
            temp = temp.dir;
        }

        Celula linhaAcima = inicio;
        for (int i = 1; i < linhas; i++) {
            Celula novaLinha = new Celula(0);
            linhaAcima.inf = novaLinha;
            novaLinha.sup = linhaAcima;

            Celula colunaAcima = linhaAcima.dir;
            temp = novaLinha;
            for (int j = 1; j < colunas; j++) {
                temp.dir = new Celula(0);
                temp.dir.esq = temp;
                temp.dir.sup = colunaAcima;
                colunaAcima.inf = temp.dir;

                temp = temp.dir;
                colunaAcima = colunaAcima.dir;
            }
            linhaAcima = novaLinha;
        }
    }

    public void mostrarDiagonalPrincipal() {
        Celula temp = inicio;
        for (int i = 0; i < linhas && i < colunas; i++) {
            System.out.print(temp.elemento + " ");
            temp = temp.inf != null && temp.dir != null ? temp.inf.dir : null;
        }
        System.out.println();
    }

    public void mostrarDiagonalSecundaria() {
        Celula temp = inicio;
        for (int i = 1; i < colunas; i++) {
            temp = temp.dir;
        }
        for (int i = 0; i < linhas && i < colunas; i++) {
            System.out.print(temp.elemento + " ");
            temp = temp.inf != null && temp.esq != null ? temp.inf.esq : null;
        }
        System.out.println();
    }

    public MatrizDinamica soma(MatrizDinamica outra) {

        MatrizDinamica resultado = new MatrizDinamica(linhas, colunas);
        Celula temp1 = this.inicio, temp2 = outra.inicio, tempR = resultado.inicio;

        for (int i = 0; i < linhas; i++) {
            Celula col1 = temp1, col2 = temp2, colR = tempR;
            for (int j = 0; j < colunas; j++) {
                colR.elemento = col1.elemento + col2.elemento;
                col1 = col1.dir;
                col2 = col2.dir;
                colR = colR.dir;
            }
            temp1 = temp1.inf;
            temp2 = temp2.inf;
            tempR = tempR.inf;
        }

        return resultado;
    }

    public void preencherLinha(int linha, String[] elementos) {
        Celula temp = inicio;
        for (int i = 0; i < linha; i++) {
            temp = temp.inf;
        }
        for (int j = 0; j < colunas; j++) {
            if (j < elementos.length) {
                temp.elemento = Integer.parseInt(elementos[j]);
            }
            temp = temp.dir;
        }
    }

    public MatrizDinamica multiplicacao(MatrizDinamica outra) {
        MatrizDinamica resultado = new MatrizDinamica(this.linhas, outra.colunas);
        for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < outra.colunas; j++) {
                Celula tempResult = resultado.getCelula(i, j);
                tempResult.elemento = 0;

                Celula tempThis = this.getCelula(i, 0);
                Celula tempOther = outra.getCelula(0, j);
                for (int k = 0; k < this.colunas; k++) {
                    tempResult.elemento += tempThis.elemento * tempOther.elemento;
                    tempThis = tempThis.dir;
                    tempOther = tempOther.inf;
                }
            }
        }
        return resultado;
    }

    public void mostrar() {
        Celula linha = inicio;
        while (linha != null) {
            Celula coluna = linha;
            while (coluna != null) {
                System.out.print(coluna.elemento + " ");
                coluna = coluna.dir;
            }
            System.out.println();
            linha = linha.inf;
        }
    }

    private Celula getCelula(int linha, int coluna) {
        Celula temp = inicio;
        for (int i = 0; i < linha; i++) temp = temp.inf;
        for (int j = 0; j < coluna; j++) temp = temp.dir;
        return temp;
    }
}


class Celula {
    public int elemento;
    public Celula esq;
    public Celula dir;
    public Celula sup;
    public Celula inf;

    public Celula() {
        this.elemento = 0;
        this.esq = this.dir = this.sup = this.inf = null;
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        this.esq = this.dir = this.sup = this.inf = null;
    }
}

class PilhaFlexivel {
    private Personagem topo;

    public PilhaFlexivel() {
        this.topo = null;
    }

    public void empilhar(Personagem p) {
        p.prox = topo;
        topo = p;
    }

    public Personagem desempilhar() {
        Personagem p = topo;
        topo = topo.prox;
        p.prox = null;
        return p;
    }

    public void mostrar() {
        Personagem atual = topo;
        System.out.println("[ Top ]");
        int contador = 0;
        while (atual != null) {
            atual.setContador(contador++);
            System.out.println(atual);
            atual = atual.prox;
        }
        System.out.println("[ Bottom ]");
    }
}

class PilhaSequencial {
    private Personagem[] personagens;
    private int topo;

    public PilhaSequencial(Data data) {
        this.personagens = new Personagem[data.getPersonagens().size()];
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

    void mostrar() {
        System.out.println("[ Top ]");
        int contador = 0;
        for (int i = topo; i >= 0; i--) {
            personagens[i].setContador(contador++);
            mostrar(i);
        }
        System.out.println("[ Bottom ]");
    }

    void mostrar(int topo) {
        System.out.println(personagens[topo]);
    }
}

class ListaFlexivel {
    Personagem primeiro, ultimo;
    int tamanho;

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
        tamanho++;
    }

    public void inserirFim(Personagem p) {
        ultimo.prox = p;
        ultimo = p;
        ultimo.prox = null;
        tamanho++;
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

            tamanho++;
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
        Personagem p = null;
        int len = len();
        if (pos < 0 || pos >= len) {
            throw new IndexOutOfBoundsException("erro: " + pos);
        } else if (pos == 0) {
            p = removerInicio();
        } else if (pos == len - 1) {
            p = removeFim();
        } else {
            Personagem i = primeiro;
            for (int j = 0; j < pos; i = i.prox, j++) ;

            Personagem temp = i.prox;
            i.prox = temp.prox;
            p = temp;
        }
        return p;
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
        int contador = 0;
        while (temp != null) {
            temp.setContador(contador++);
            System.out.println(temp);
            temp = temp.prox;
        }
    }
}

class ListaSequencial {
    Personagem[] personagens;
    int tamanho;

    public ListaSequencial(Data data) {
        this.personagens = new Personagem[data.getPersonagens().size()];
        this.tamanho = 0;
    }

    void insereInicio(Personagem p) {
        if (tamanho >= personagens.length) {
            return;
        }
        for (int i = tamanho; i > 0; i--) {
            personagens[i] = personagens[i - 1];
        }
        personagens[0] = p;
        tamanho++;
    }

    void inserePos(Personagem p, int pos) {
        if (tamanho >= personagens.length) {
            return;
        }
        if (pos < 0 || pos > tamanho) {
            return;
        }
        for (int i = tamanho; i > pos; i--) {
            personagens[i] = personagens[i - 1];
        }
        personagens[pos] = p;
        tamanho++;
    }

    void insereFim(Personagem p) {
        if (tamanho >= personagens.length) {
            return;
        }
        personagens[tamanho] = p;
        tamanho++;
    }

    Personagem removeInicio() {
        if (tamanho == 0) {
            return null;
        }
        Personagem p = personagens[0];
        for (int i = 0; i < tamanho - 1; i++) {
            personagens[i] = personagens[i + 1];
        }
        personagens[tamanho - 1] = null;
        tamanho--;
        return p;
    }

    Personagem removePos(int pos) {
        if (tamanho == 0) {
            return null;
        }
        if (pos < 0 || pos >= tamanho) {
            return null;
        }
        Personagem p = personagens[pos];
        for (int i = pos; i < tamanho - 1; i++) {
            personagens[i] = personagens[i + 1];
        }
        personagens[tamanho - 1] = null;
        tamanho--;
        return p;
    }

    Personagem removeFim() {
        if (tamanho == 0) {
            return null;
        }
        Personagem p = personagens[tamanho - 1];
        personagens[tamanho - 1] = null;
        tamanho--;
        return p;
    }

    void mostrar() {
        int contador = 0;
        for (int i = 0; i < tamanho; i++) {
            personagens[i].setContador(contador++);
            System.out.println(personagens[i]);
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
        int contador = 0;

        return new Personagem(id.toString(), name, alternateNames, house, ancestry, species, patronus, hogwartsStaff,
                hogwartsStudent, actorName, alive, dateOfBirth, yearOfBirth, eyeColour, gender, hairColour, wizard, contador);
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
    private int contador;

    public Personagem() {
        _id = null;
        _yearOfBirth = 0;
        _name = "";
        _alternateNames = new ArrayList<>();
        _house = "";
        _ancestry = "";
        _species = "";
        _patronus = "";
        _hogwartsStaff = false;
        _hogwartsStudent = "";
        _actorName = "";
        _alive = false;
        _eyeColour = "";
        _gender = "";
        _hairColour = "";
        _dateOfBirth = new Date(0);
        _wizard = false;
        this.prox = null;
        this.contador = 0;
    }

    public Personagem(String id, String name, ArrayList<String> alternateNames, String house, String ancestry,
                      String species, String patronus, boolean hogwartsStaff, String hogwartsStudent, String actorName,
                      boolean alive, Date dateOfBirth, int yearOfBirth, String eyeColour, String gender, String hairColour,
                      boolean wizard, int contador) {

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
        this.contador = contador;
    }

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

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = (_dateOfBirth != null) ? sdf.format(_dateOfBirth) : "";

        String alternateNamesFormatted = Data.replaceAllSimple(
                Data.replaceAllSimple(Data.replaceAllSimple(this._alternateNames.toString(), "[", ""), "]", ""), "'",
                "");

        return String.format(
                "[%d ## %s ## %s ## {%s} ## %s ## %s ## %s ## %s ## %b ## %b ## %s ## %b ## %s ## %d ## %s ## %s ## %s ## %b]",
                this.contador, this._id, this._name, alternateNamesFormatted, this._house, this._ancestry, this._species,
                this._patronus, this._hogwartsStaff, this._hogwartsStudent.equalsIgnoreCase("VERDADEIRO"),
                this._actorName, this._alive, formattedDate, this._yearOfBirth, this._eyeColour, this._gender,
                this._hairColour, this._wizard);
    }
}