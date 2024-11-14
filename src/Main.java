import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;                                  //кол-во элементов
        while (true) {
            if(sc.hasNextInt()) {
                n = sc.nextInt();
                if(n < 2 || n > Math.pow(10, 5)){
                    System.out.println("Invalid input");            //проверка на выход из диапазона(в задаче указан)
                } else {
                    break;
                }
            } else {
                System.out.println("Please enter a number");          //если введено не число
                sc.next();
            }
        }
        ArrayList<Integer> list = new ArrayList<>();              //лист для хранения чисел
        for(int i = 0; i < n; i++) {
            while(true) {
                if(sc.hasNextInt()) {
                    int a = sc.nextInt();
                    if(a < 1 || a > n){
                        System.out.println("Invalid input");            //проверка на диапазон
                    } else {
                        list.add(a);
                        break;
                    }
                } else {
                    System.out.println("Please enter a number");          //если не число
                    sc.next();
                }
            }
        }
        boolean isSetLast = false;         //переменная для знания, было ли изменено последнее число на 1(если последнее число не 1, не получится)
        if(list.getLast() != 1) {
            list.set(list.size() - 1, 1);
            isSetLast = true;
        }
        int sameIndexAndNumCount = 0;           //счётчик числе равным индексу
        int sameIndexAndNum = 0;                //сохранение индекса, с которым совпадает его число(понадобится в конце)
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) == i+1) {
                sameIndexAndNumCount++;
                sameIndexAndNum = i;
                if(sameIndexAndNumCount > 1) {           //если больше одного, то не получится. По условию, можно изменить только одно число
                    System.out.println("-1 -1");
                    return;
                }
            }
        }
        TreeSet<Integer> sameNumber = new TreeSet<>();         //дерево для хранения уникальных повторяющихся чисел в последовательности
        for(int i = 0; i < list.size(); i++) {
            int a = list.get(i);
            int sameNumCount = 0;                       //счётчик повторяющихся чисел
            for(int j = i+1; j < list.size(); j++) {
                if(a == list.get(j)) {
                    sameNumber.add(a);                   //добавляем повторяющееся число
                    sameNumCount++;
                    if(sameNumber.size() > 1 || isSetLast || sameNumCount > 1) {    //если повторяющихся чисел больше 1, или нашло повторяющееся, но последний элемент был изменён, то не получится
                        System.out.println("-1 -1");
                        return;
                    }
                }
            }
        }
        if(sameNumber.size() == 1 && sameIndexAndNumCount == 1) {   //если есть повторяющееся число и есть индекс с совпадающим число
            if(!sameNumber.contains(list.get(sameIndexAndNum))) {             //если числа не совпадают, то не получится. Нужно будет изменить больше 1 числа
                System.out.println("-1 -1");
                return;
            }
        }
        if(sameNumber.size() == 1) {         //если повторяющееся число
            int missingNum = findMissingNumber(list);         //находим элемент, которого нет в последовательности (все элементы должны быть уникальны)
            int indexToSet;                                   //переменная для сохранения индекса, число которого нужно изменить
            if(list.indexOf(sameNumber.getFirst()) == missingNum){               //если индекс, где впервые встречается повторяющееся число, совпадает с пропущенным числом
                indexToSet = list.lastIndexOf(sameNumber.getFirst()) + 1;           //ставим пропущенное число на то место, где повторяющееся встречается второй раз(запоминаем индекс)
                System.out.println(indexToSet+" "+missingNum);
            } else {
                indexToSet = list.indexOf(sameNumber.getFirst()) + 1;         //иначе, ставим на место, где впервые встречается(запоминаем индекс)
                System.out.println(indexToSet+" "+missingNum);
            }
            return;
        } else if(sameIndexAndNumCount == 1) {         //если число совпадает со своим индексом
            int missingNum = findMissingNumber(list);          //находим пропущенное число
            if(missingNum == 0) {                            //если нет пропущенных, то не получится
                System.out.println("-1 -1");
                return;
            }
            int indexToSet = sameIndexAndNum + 1;          //присваиваем индекс с которым совпадает число
            System.out.println(indexToSet+" "+missingNum);
            return;
        }
        if(isSetLast) {
            System.out.println(n+" "+1);   //если нужно было только заменить последнее значение на 1, выводим
        }
    }

    private static int findMissingNumber(ArrayList<Integer> list) {          //поиск пропущенного числа
        HashSet<Integer> set = new HashSet<>(list);              //хэш сет для того, чтобы убрать не уникальные элементы
        ArrayList<Integer> sortedList = new ArrayList<>(set);         //лист с уникальными элементами для сортировки
        int missingNum = 0;
        for(int i = 1; i <= sortedList.size(); i++) {
            if(!sortedList.contains(i)){       //если число не совпало со своим индексом, то пропущенный элемент индекс
                missingNum = i;
                break;
            }
        }
        return missingNum;
    }
}
