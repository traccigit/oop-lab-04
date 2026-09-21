import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    // 1. Возвращает среднее значение списка целых чисел
    public static double getAverage(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    // 2. Приводит все строки в верхний регистр
    // и добавляет к ним префикс "new"
    public static List<String> toUpperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(String::toUpperCase)
                .map(string -> "new" + string)
                .collect(Collectors.toList());
    }

    // 3. Возвращает список квадратов элементов,
    // встречающихся в исходном списке только один раз
    public static List<Integer> getUniqueSquares(List<Integer> numbers) {
        Map<Integer, Long> frequencies = numbers.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        return numbers.stream()
                .filter(number -> frequencies.get(number) == 1)
                .map(number -> number * number)
                .collect(Collectors.toList());
    }

    // 4. Возвращает последний элемент коллекции.
    // Если коллекция пуста, выбрасывает исключение
    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(NoSuchElementException::new);
    }

    // 5. Возвращает сумму чётных чисел массива.
    // Если чётных чисел нет, возвращается 0
    public static int getEvenSum(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(number -> number % 2 == 0)
                .sum();
    }

    // 6. Преобразует список строк в Map:
    // первый символ строки — ключ,
    // оставшаяся часть строки — значение
    public static Map<Character, String> stringsToMap(List<String> strings) {
        return strings.stream()
                .collect(Collectors.toMap(
                        string -> string.charAt(0),
                        string -> string.substring(1)
                ));
    }

    // Чтение списка целых чисел
    private static List<Integer> readIntegerList(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println(
                        "Список не должен быть пустым. Повторите ввод:"
                );
                continue;
            }

            try {
                return Arrays.stream(line.split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.out.println(
                        "Ошибка. Введите целые числа через пробел:"
                );
            }
        }
    }

    // Чтение массива целых чисел
    private static int[] readIntegerArray(Scanner scanner) {
        return readIntegerList(scanner).stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    // Чтение непустого списка строк
    private static List<String> readStringList(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println(
                        "Список не должен быть пустым. Повторите ввод:"
                );
                continue;
            }

            List<String> strings = Arrays.stream(line.split(";"))
                    .map(String::trim)
                    .collect(Collectors.toList());

            boolean hasEmptyString = strings.stream()
                    .anyMatch(String::isEmpty);

            if (hasEmptyString) {
                System.out.println(
                        "Строки не должны быть пустыми. Повторите ввод:"
                );
                continue;
            }

            return strings;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("Выберите метод:");
            System.out.println(
                    "1 - Среднее значение списка целых чисел"
            );
            System.out.println(
                    "2 - Верхний регистр и префикс \"new\""
            );
            System.out.println(
                    "3 - Квадраты элементов, встречающихся один раз"
            );
            System.out.println(
                    "4 - Последний элемент коллекции"
            );
            System.out.println(
                    "5 - Сумма чётных чисел массива"
            );
            System.out.println(
                    "6 - Преобразование списка строк в Map"
            );
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            String input = scanner.nextLine().trim();

            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Ошибка: введите номер от 0 до 6."
                );
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.println(
                            "Введите целые числа через пробел:"
                    );

                    List<Integer> averageNumbers =
                            readIntegerList(scanner);

                    System.out.println(
                            "Среднее значение: "
                                    + getAverage(averageNumbers)
                    );
                    break;

                case 2:
                    System.out.println(
                            "Введите строки через точку с запятой (;):"
                    );

                    List<String> strings =
                            readStringList(scanner);

                    System.out.println(
                            "Результат: "
                                    + toUpperCaseWithPrefix(strings)
                    );
                    break;

                case 3:
                    System.out.println(
                            "Введите целые числа через пробел:"
                    );

                    List<Integer> uniqueNumbers =
                            readIntegerList(scanner);

                    System.out.println(
                            "Квадраты элементов, встречающихся один раз: "
                                    + getUniqueSquares(uniqueNumbers)
                    );
                    break;

                case 4:
                    System.out.println(
                            "Введите элементы коллекции через точку с запятой (;)."
                    );
                    System.out.println(
                            "Для пустой коллекции просто нажмите Enter:"
                    );

                    String collectionInput =
                            scanner.nextLine().trim();

                    List<String> collection =
                            collectionInput.isEmpty()
                                    ? List.of()
                                    : Arrays.stream(
                                                    collectionInput.split(";")
                                            )
                                            .map(String::trim)
                                            .collect(Collectors.toList());

                    try {
                        System.out.println(
                                "Последний элемент: "
                                        + getLastElement(collection)
                        );
                    } catch (NoSuchElementException e) {
                        System.out.println(
                                "Ошибка: коллекция пуста."
                        );
                    }
                    break;

                case 5:
                    System.out.println(
                            "Введите целые числа через пробел:"
                    );

                    int[] array =
                            readIntegerArray(scanner);

                    System.out.println(
                            "Сумма чётных чисел: "
                                    + getEvenSum(array)
                    );
                    break;

                case 6:
                    System.out.println(
                            "Введите строки через точку с запятой (;)."
                    );
                    System.out.println(
                            "Первые символы строк должны различаться:"
                    );

                    List<String> mapStrings =
                            readStringList(scanner);

                    try {
                        System.out.println(
                                "Результат: "
                                        + stringsToMap(mapStrings)
                        );
                    } catch (IllegalStateException e) {
                        System.out.println(
                                "Ошибка: несколько строк имеют "
                                        + "одинаковый первый символ."
                        );
                    }
                    break;

                case 0:
                    System.out.println("Программа завершена.");
                    scanner.close();
                    return;

                default:
                    System.out.println(
                            "Ошибка: выберите пункт от 0 до 6."
                    );
            }
        }
    }
}