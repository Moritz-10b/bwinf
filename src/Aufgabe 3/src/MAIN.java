import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MAIN {
    static File file = new File("rsc/worte3.txt");
    static ArrayList<String> data = new ArrayList<>();
    static Random random = new Random();
    static char randomChar;
    static char[][] field;
    static char[][] fieldCopy;
    static int height, width, amountWords, x, y;
    static ArrayList<Integer> FreeZeilen, FreeSpalten;
    static ArrayList<Character> letters;
    static ArrayList<String> Words;

    public static void main (String[]args) throws FileNotFoundException {
        mainVoid(5, 4);     //von 1 bis 5 !
    }

    public static void mainVoid(int Level, int Quelle) throws FileNotFoundException {
        System.out.println("Level " + Level + ", WörterDatei " + Quelle);
        file = new File("rsc/worte" + Quelle + ".txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            data.add(sc.next());
        }
        height = Integer.parseInt(data.get(0));
        width = Integer.parseInt(data.get(1));
        amountWords = Integer.parseInt(data.get(2));
        Words = new ArrayList<String>();
        FreeZeilen = new ArrayList<Integer>();
        FreeSpalten = new ArrayList<Integer>();

        for (int i = 0; i < amountWords; i++) {
            Words.add (data.get(i + 3));
        }
        Words.sort(Comparator.comparingInt(String::length));
        Collections.reverse(Words);
        //System.out.println(Words);

        field = new char[height][width];
        fieldCopy = new char[height][width];

        letters = new ArrayList<>();


        while(Words.size() > 0){

            for (int i = 0; i < Words.size(); i++) {
                int direction = random.nextInt(Level);
                checkLetters(Words.get(i), direction);
            }
            ArrayList<String> WordsTrash = new ArrayList<>();
            for (String Word : Words){
                if(Word == "a"){
                    WordsTrash.add(Word);
                }
            }
            Words.removeAll(WordsTrash);
            WordsTrash.clear();
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                randomChar = (char) ('A' + 23 * Math.random());
                if (field[i][j] == '\u0000') {
                    field[i][j] = randomChar;
                    fieldCopy[i][j] = randomChar;
                }
            }
        }

        for (int zeile = 0; zeile < field.length; zeile++) {
            System.out.print(zeile + ": ");
            for (int spalte = 0; spalte < field[zeile].length; spalte++) {
                if (field[zeile][spalte] != fieldCopy[zeile][spalte]) {
                    System.out.print("\u001B[32m" + field[zeile][spalte] + "\u001B[0m" + " ");
                } else {
                    System.out.print(field[zeile][spalte] + " ");
                }
            }
            System.out.println();
        }

    }

    public static void checkLetters(String Word, int direction) {
        boolean fits;
        switch (direction) {
            case 5 -> {
                //System.out.println(Word + " diagonal bw");
                if (Word == Words.get(0)) {
                    //System.out.println("false");
                    break;
                }
                if ((Word.length() > width && width < height) || (Word.length() > height && height < width)) {
                    //System.out.println("false");
                    break;
                }
                else if (width == Word.length() && height == Word.length()) {
                    x = 0;
                    y = 0;
                } else if (width > height && Word.length() == height) {
                    y = 0;
                    x = random.nextInt(width - Word.length());
                } else if (height > width && Word.length() == height) {
                    x = 0;
                    y = random.nextInt(height - Word.length());
                } else if (Word.length() == width && width == height) {
                    y = 0;
                    x = 0;
                } else {
                    y = random.nextInt(height - Word.length());
                    x = random.nextInt(width - Word.length());
                }
                letters.clear();
                for (int j = 0; j < Word.length(); j++) {
                    letters.add(Word.charAt(j));
                }
                Collections.reverse(letters);
                fits = false;
                for (int j = 0; j < Word.length(); j++) {
                    if (field[y + j][x + j] != fieldCopy[y + j][x + j]) {
                        if (letters.get(j) == field[y + j][x + j]) {
                            fits = true;
                        } else {
                            fits = false;
                            break;
                        }
                    } else if (field[y + j][x + j] == fieldCopy[y + j][x + j]) {
                        fits = true;
                    }
                }
                //System.out.println(fits);
                if (fits) {
                    for (int i = 0; i < Word.length(); i++) {
                        field[y + i][x + i] = letters.get(i);
                    }
                    Words.set(Words.indexOf(Word), "a");

                }
                //System.out.println(Words);
            }
            case 4 -> {
                //System.out.println(Word + " vertikal bw");
                if (Word.length() > height) {
                    //System.out.println("false");
                    break;
                } else if (height == Word.length()) {
                    x = random.nextInt(width - 1);
                    y = 0;
                } else {
                    x = random.nextInt(width - 1);
                    y = random.nextInt(height - Word.length());
                }
                letters.clear();
                for (int j = 0; j < Word.length(); j++) {
                    letters.add(Word.charAt(j));
                }
                Collections.reverse(letters);
                fits = false;
                for (int j = 0; j < Word.length(); j++) {
                    if (field[y + j][x] != fieldCopy[y + j][x]) {
                        if (letters.get(j) == field[y + j][x]) {
                            fits = true;
                        } else {
                            fits = false;
                            break;
                        }
                    } else {
                        fits = true;
                    }
                }
                //System.out.println(fits);
                if (fits) {
                    for (int i = 0; i < Word.length(); i++) {
                        field[y + i][x] = letters.get(i);
                    }
                    Words.set(Words.indexOf(Word), "a");
                }
                //System.out.println(Words);
            }
            case 3 -> {
                //System.out.println(Word + " horizontal bw");
                if (Word.length() > width) {
                    //System.out.println("false");
                    break;
                } else if (width == Word.length()) {
                    x = 0;
                    y = random.nextInt(height - 1);
                } else {
                    x = random.nextInt(width - Word.length());
                    y = random.nextInt(height - 1);
                }
                letters.clear();
                for (int j = 0; j < Word.length(); j++) {
                    letters.add(Word.charAt(j));
                }
                Collections.reverse(letters);
                fits = false;
                for (int j = 0; j < Word.length(); j++) {
                    if (field[y][x + j] != fieldCopy[y][x + j]) {
                        if (letters.get(j) == field[y][x + j]) {
                            fits = true;
                        } else {
                            fits = false;
                            break;
                        }
                    } else {
                        fits = true;
                    }
                }
                //System.out.println(fits);
                if (fits) {
                    for (int i = 0; i < Word.length(); i++) {
                        field[y][x + i] = letters.get(i);
                    }
                    Words.set(Words.indexOf(Word), "a");
                }
                //System.out.println(Words);
            }
            case 2 -> {
                //System.out.println(Word + " diagonal");
                if (Word == Words.get(0)) {
                    //System.out.println("false");
                    break;
                }
                if ((Word.length() > width && width < height) || (Word.length() > height && height < width)) {
                    //System.out.println("false");
                    break;
                }            //länger als kurze seite?
                else if (width == Word.length() && height == Word.length()) {
                    x = 0;
                    y = 0;
                } else if (width > height && Word.length() == height) {
                    y = 0;
                    x = random.nextInt(width - Word.length());
                } else if (height > width && Word.length() == height) {
                    x = 0;
                    y = random.nextInt(height - Word.length());
                } else if (Word.length() == width && width == height) {
                    y = 0;
                    x = 0;
                } else {
                    y = random.nextInt(height - Word.length());
                    x = random.nextInt(width - Word.length());
                }
                letters.clear();
                for (int j = 0; j < Word.length(); j++) {
                    letters.add(Word.charAt(j));
                }
                fits = false;
                for (int j = 0; j < Word.length(); j++) {
                    if (field[y + j][x + j] != fieldCopy[y + j][x + j]) {
                        if (letters.get(j) == field[y + j][x + j]) {
                            fits = true;
                        } else {
                            fits = false;
                            break;
                        }
                    } else if (field[y + j][x + j] == fieldCopy[y + j][x + j]) {
                        fits = true;
                    }
                }
                //System.out.println(fits);
                if (fits) {
                    for (int i = 0; i < Word.length(); i++) {
                        field[y + i][x + i] = letters.get(i);
                    }
                    Words.set(Words.indexOf(Word), "a");
                }
                //System.out.println(Words);
            }
            case 0 -> {
                //System.out.println(Word + " horizontal");
                if (Word.length() > width) {
                    //System.out.println("false");
                    break;
                } else if (width == Word.length()) {
                    x = 0;
                    y = random.nextInt(height - 1);
                } else {
                    x = random.nextInt(width - Word.length());
                    y = random.nextInt(height - 1);
                }
                letters.clear();
                for (int j = 0; j < Word.length(); j++) {
                    letters.add(Word.charAt(j));
                }
                fits = false;
                for (int j = 0; j < Word.length(); j++) {
                    if (field[y][x + j] != fieldCopy[y][x + j]) {
                        if (letters.get(j) == field[y][x + j]) {
                            fits = true;
                        } else {
                            fits = false;
                            break;
                        }
                    } else {
                        fits = true;
                    }
                }
                //System.out.println(fits);
                if (fits) {
                    for (int i = 0; i < Word.length(); i++) {
                        field[y][x + i] = letters.get(i);
                    }
                    Words.set(Words.indexOf(Word), "a");
                }
                //System.out.println(Words);
            }
            case 1 -> {
                //System.out.println(Word + " vertikal");
                if (Word.length() > height) {
                    //System.out.println("false");
                    break;
                } else if (height == Word.length()) {
                    x = random.nextInt(width - 1);
                    y = 0;
                } else {
                    x = random.nextInt(width - 1);
                    y = random.nextInt(height - Word.length());
                }
                letters.clear();
                for (int j = 0; j < Word.length(); j++) {
                    letters.add(Word.charAt(j));
                }
                fits = false;
                for (int j = 0; j < Word.length(); j++) {
                    if (field[y + j][x] != fieldCopy[y + j][x]) {
                        if (letters.get(j) == field[y + j][x]) {
                            fits = true;
                        } else {
                            fits = false;
                            break;
                        }
                    } else {
                        fits = true;
                    }
                }
                //System.out.println(fits);
                if (fits) {
                    for (int i = 0; i < Word.length(); i++) {
                        field[y + i][x] = letters.get(i);
                    }
                    Words.set(Words.indexOf(Word), "a");
                }
                //System.out.println(Words);
            }
        }
    }
}
