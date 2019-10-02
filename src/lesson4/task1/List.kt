@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.isPrime
import kotlin.math.sqrt
import kotlin.math.pow

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.map { it * it }.sum())

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = when {
    list.isEmpty() -> 0.0
    else -> list.sum() / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val mean = mean(list)
    for (i in 0 until list.size) {
        list[i] -= mean
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var ans = 0
    for (i in 0 until a.size) {
        ans += a[i] * b[i]
    }
    return ans
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var ans = 0
    for (i in 0 until p.size) {
        ans += p[i] * x.toDouble().pow(i).toInt()
    }
    return ans
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size) {
        list[i] += list[i - 1]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var m = n
    val result = mutableListOf<Int>()
    if (!isPrime(m))
        for (i in 2..m) {
            while ((m % i).toDouble() == 0.0) {
                result.add(i)
                m /= i
            }
        }
    else result.add(m)
    return result
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val result = mutableListOf<Int>()
    var m = n
    while (m != 0) {
        result.add(m % base)
        m /= base
    }
    if (m != 0 || result.isEmpty()) result.add(m)
    return result.reversed()
}

/**
 *
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
const val asc2let = 87
const val asc2num = 48

fun convertToString(n: Int, base: Int): String { // ПЕРЕДЕЛАТЬ!
    val list = convert(n, base)
    var answer = String()
    for (i in 0 until list.size) {
        answer += if (list[i] >= 10) (list[i] + asc2let).toChar()
        else list[i]
    }
    return answer
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var result = 0
    for (i in 0 until digits.size) {
        result += digits[i] * base.toDouble().pow(digits.size - i - 1).toInt()
    }
    return result
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    val answer = mutableListOf<Int>()
    for (i in 0 until str.length) {
        if (str[i].toInt() in 48..57) {
            answer.add(i, str[i].toInt() - asc2num)
        } else {
            answer.add(i, str[i].toInt() - asc2let)
        }
    }
    return decimal(answer, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String { // ДОДЕЛАТЬ ПЕРЕДЕЛЫВАНИЕ!
    val romnum = listOf('M', 'D', 'C', 'L', 'X', 'V', 'I')
    var m = n
    var i = 0
    var x = 1000
    var y = 900
    var z = 400
    var result = String()
    while (m != 0) {
        for (i in 1..m / x) {
            result += romnum[i].toString() + asc2let
            m %= x
            result += romnum[i].toString() + asc2let
            m %= y
        }

    }
    if (m / 1000 != 0) {

    }
    if (m / 900 != 0) {
        for (i in 1..m / 900) result += "CM"
        m %= 900
    }
    if (m / 500 != 0) {
        for (i in 1..m / 500) result += "D"
        m %= 500
    }
    if (m / 400 != 0) {
        for (i in 1..m / 400) result += "CD"
        m %= 400
    }
    if (m / 100 != 0) {
        for (i in 1..m / 100) result += "C"
        m %= 100
    }
    if (m / 90 != 0) {
        for (i in 1..m / 90) result += "XC"
        m %= 90
    }
    if (m / 50 != 0) {
        for (i in 1..m / 50) result += "L"
        m %= 50
    }
    if (m / 40 != 0) {
        for (i in 1..m / 40) result += "XL"
        m %= 40
    }
    if (m / 10 != 0) {
        for (i in 1..m / 10) result += "X"
        m %= 10
    }
    if (m / 9 != 0) {
        for (i in 1..m / 9) result += "IX"
        m %= 9
    }
    if (m / 5 != 0) {
        for (i in 1..m / 5) result += "V"
        m %= 5
    }
    if (m / 4 != 0) {
        for (i in 1..m / 4) result += "IV"
        m %= 4
    }
    if (m / 1 != 0) for (i in 1..m / 1) result += "I"
    return result
}


/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val m = n.toString()
    val num = mutableListOf<Int>()
    val answer = mutableListOf<String>()
    val hundreds =
        listOf(
            "сто", "двести", "триста", "четыреста",
            "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"
        )
    val tens10to19 =
        listOf(
            "десять", "одиннадцать", "двенадцать", "тринадцать",
            "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать",
            "восемнадцать", "девятнадцать"
        )
    val tens20to90 =
        listOf(
            "десять", "двадцать", "тридцать", "сорок", "пятьдесят",
            "шестьдесят", "семьдесят", "восемьдесят", "девяносто"
        )
    val units1000 =
        listOf(
            "одна", "две", "три", "четыре", "пять",
            "шесть", "семь", "восемь", "девять"
        )
    val units1 =
        listOf(
            "один", "два", "три", "четыре", "пять",
            "шесть", "семь", "восемь", "девять"
        )
    for (i in 0 until m.length) {
        num.add(m[i].toInt() - asc2num)
    }
    var i = m.length
    for (j in 0 until num.size) {
        when (i) {
            6, 3 -> if (num[j] != 0) answer.add(hundreds[num[j] - 1])
            5, 2 ->
                when {
                    num[j] == 1 -> answer.add(tens10to19[num[j + 1]])
                    num[j] != 0 -> answer.add(tens20to90[num[j] - 1])
                }
            4 -> {
                if ((num[j] != 0) && ((num.size == 4) || (num[j - 1] != 1))) {
                    answer.add(units1000[num[j] - 1])
                    when (num[j]) {
                        1 -> answer.add("тысяча")
                        2, 3, 4 -> answer.add("тысячи")
                        else -> answer.add("тысяч")
                    }
                } else answer.add("тысяч")
            }
            1 -> {
                if ((num[j] != 0) && ((num.size == 1) || (num[j - 1] != 1)))
                    answer.add(units1[num[j] - 1])
            }
        }
        i--
    }
    return answer.joinToString(separator = " ")
}