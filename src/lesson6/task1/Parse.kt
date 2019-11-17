@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months = listOf(
    "", "января", "февраля", "марта", "апреля", "мая", "июня",
    "июля", "августа", "сентября", "октября", "ноября", "декабря"
)

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    val day = parts[0].toIntOrNull() ?: return ""
    val month: Int
    if (parts[1] in months) {
        month = months.indexOf(parts[1])
    } else return ""
    val year = parts[2].toIntOrNull() ?: return ""
    if (daysInMonth(month, year) < day) return ""
    return String.format("%02d.%02d.%d", day, month, year)
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val day = parts[0].toIntOrNull() ?: return ""
    val month: String
    if (parts[1].toIntOrNull() != null && parts[1].toIntOrNull()!! > 0 &&
        parts[1].toIntOrNull()!! < months.size
    ) {
        month = months[parts[1].toIntOrNull()!!]
    } else return ""
    val year = parts[2].toIntOrNull() ?: return ""
    if (daysInMonth(parts[1].toInt(), year) < day) return ""
    return String.format("%d %s %d", day, month, year)
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    if (phone == "") return ""
    val answer = StringBuilder()
    if (Regex("""[^-+() 0123456789]""").containsMatchIn(phone)) return ""
    if ("(" in phone || ")" in phone) {
        if (!("(" in phone && ")" in phone) ||
            phone.filter { it == '(' }.length != phone.filter { it == ')' }.length ||
            !Regex("""\d+""").containsMatchIn(phone.substring(phone.indexOf("("), phone.indexOf(")")))
        ) return ""
    }
    if (phone.indexOf("+") == 0) {
        if (Regex("""\d+""").containsMatchIn(phone.substring(phone.indexOf("+")))) {
            answer.append("+")
        } else return ""
    }
    for (element in Regex("""\d+""").findAll(phone)) {
        answer.append(element.value)
    }
    return answer.toString()
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (Regex("""[^-% 0123456789]""").containsMatchIn(jumps)) return -1
    val searchMaxJump = Regex("""\d+""").findAll(jumps)
    var maxJump = -1
    for (element in searchMaxJump) {
        if (element.value.toInt() > maxJump) maxJump = element.value.toInt()
    }
    return maxJump
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (Regex("""[^-+% 0123456789]""").containsMatchIn(jumps)) return -1
    val searchMaxJump = mutableListOf<String>()
    searchMaxJump.addAll(Regex("""-|%|\+|\d+""").findAll(jumps).map { it.value.trim() })
    var maxJump = -1
    for (i in 0 until searchMaxJump.size - 1) {
        if (searchMaxJump[i + 1] == "+") {
            if (searchMaxJump[i].toIntOrNull() != null && searchMaxJump[i].toInt() > maxJump) {
                maxJump = searchMaxJump[i].toInt()
            }
        }
    }
    return maxJump
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val searchExpEl = Regex("""[ ]""").split(expression)
    if (searchExpEl.size % 2 == 0) throw IllegalArgumentException()
    if (!"$expression ".contains(Regex("""\d+""")) ||
        Regex("""[^-+ 0123456789]""").containsMatchIn(expression) ||
        Regex("""[^0123456789]""").containsMatchIn(searchExpEl[0])
    ) throw IllegalArgumentException()
    var answer = searchExpEl[0].toInt()
    for (i in 1 until searchExpEl.size) {
        if (i % 2 == 1 && !Regex("""[^-+]""").containsMatchIn(searchExpEl[i])) {
            when (searchExpEl[i]) {
                "+" -> answer += searchExpEl[i + 1].toIntOrNull() ?: throw IllegalArgumentException()
                "-" -> answer -= searchExpEl[i + 1].toIntOrNull() ?: throw IllegalArgumentException()
                else -> throw IllegalArgumentException()
            }
        } else if (Regex("""[^0123456789]""").containsMatchIn(searchExpEl[i])) {
            throw IllegalArgumentException()
        }
    }
    return answer
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var counter = 0
    val listStr = str.toLowerCase().split(" ")
    for (i in 0..listStr.size - 2) {
        if (listStr[i] == listStr[i + 1]) return counter
        else counter += listStr[i].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val goodsAndPrices = Regex("""[; ]""").split(description)
    if (goodsAndPrices.size < 2) return ""
    var nameMaxPrice = goodsAndPrices[0]
    var maxPrice = goodsAndPrices[1].toDoubleOrNull() ?: return ""
    for (i in 4 until goodsAndPrices.size step 3) {
        if (goodsAndPrices[i].toDouble() > maxPrice) {
            nameMaxPrice = goodsAndPrices[i - 1]
            maxPrice = goodsAndPrices[i].toDoubleOrNull() ?: return ""
        }
    }
    return nameMaxPrice
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (Regex("""[^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})${'$'}]""")
            .containsMatchIn(roman) || roman.isEmpty()
    ) return -1
    val pairs = mapOf(
        'C' to listOf('M', 'D'),
        'X' to listOf('C', 'L'),
        'I' to listOf('X', 'V')
    )
    val romans = listOf('M', 'D', 'C', 'L', 'X', 'V', 'I')
    val numbers = listOf(1000, 500, 100, 50, 10, 5, 1)
    var count: Int
    var answer = 0
    var i = 0
    var position = 0
    var checkPosition: Int
    while (i < roman.length) {
        checkPosition = romans.indexOf(roman[i])
        if (checkPosition < position) return -1
        count = i
        while (count < roman.length && roman[count] == roman[i]) count++
        if (count < roman.length && roman[i] in pairs.keys &&
            roman[count] in pairs[roman[i]] ?: error(-1)
        ) {
            if (count - i >= 2) return -1
            answer += numbers[romans.indexOf(roman[count])] -
                    numbers[romans.indexOf(roman[i])] * (count - i)
            count++
        } else {
            answer += numbers[romans.indexOf(roman[i])] * (count - i)
        }
        position = romans.indexOf(roman[i])
        i = count
    }
    return answer
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun searchNextCommand(count: Int, commands: String, openingOrClosingBracket: Boolean): Int {
    var nextCount = count
    val target = if (openingOrClosingBracket) ']'
    else '['
    while (commands[nextCount] != target && nextCount < commands.length) {
        if (openingOrClosingBracket) {
            if (commands[nextCount] == '[') {
                nextCount = searchNextCommand(nextCount + 1, commands, true)
            }
            nextCount++
        } else {
            if (commands[nextCount] == ']') {
                nextCount = searchNextCommand(nextCount - 1, commands, false)
            }
            nextCount--
        }
    }
    return nextCount
}

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    if (Regex("""[^-+ \[\]<>]""").containsMatchIn(commands)) throw IllegalArgumentException()
    var bracketsCheck = 0
    commands.forEach {
        if (it == '[') bracketsCheck++
        else if (it == ']') {
            if (bracketsCheck != 0) bracketsCheck--
            else throw IllegalArgumentException()
        }
    }
    if (bracketsCheck != 0) throw IllegalArgumentException()
    val result = MutableList(cells) { 0 }
    var position = cells / 2
    var limitMut = limit
    var count = 0
    while (limitMut > 0 && count < commands.length) {
        when (commands[count]) {
            '+' -> result[position]++
            '-' -> result[position]--
            '>' -> position++
            '<' -> position--
            '[' -> if (result[position] == 0) {
                count = searchNextCommand(count + 1, commands, true)
            }
            ']' -> if (result[position] != 0) {
                count = searchNextCommand(count - 1, commands, false)
            }
        }
        if (position !in 0 until cells) throw IllegalStateException()
        count++
        limitMut--
    }
    return result
}