@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import ru.spbstu.wheels.toMap
import java.io.File
import kotlin.math.max
import kotlin.math.floor


/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    substrings.forEach { result[it] = 0 }
    for (line in File(inputName).readLines().filter { it.isNotEmpty() }) {
        for ((key) in result) {
            for (i in 0..line.length - key.length) {
                if (line.substring(i, i + key.length).toLowerCase().contains(key.toLowerCase())) {
                    result[key] = result[key]!! + 1
                }
            }
        }
    }
    return result
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */

fun sibilants(inputName: String, outputName: String) {
    val exceptions = listOf("жюри", "брошюра", "парашют")
    val exceptionsWithMistakes = listOf("жури", "брошура", "парашут")
    val exceptionsMistakes = mapOf(
        'у' to 'ю',
        'У' to 'Ю'
    )
    val letters = setOf('ж', 'ч', 'ш', 'щ', 'Ж', 'Ч', 'Ш', 'Щ')
    var check: Boolean
    var word: String
    val mistakes = mapOf(
        'ы' to 'и',
        'я' to 'а',
        'ю' to 'у',
        'Ы' to 'И',
        'Я' to 'А',
        'Ю' to 'У'
    )
    File(outputName).bufferedWriter().use {
        for (line in File(inputName).readLines()) {
            val splitLine = line.split(" ")
            for (i in 0 until splitLine.size) {
                word = splitLine[i]
                check = true
                if (Regex("""[^абвгдеёжзийклмнопрстуфхцчшщъыьэюя]""").replace(word.toLowerCase(), "")
                    in exceptionsWithMistakes
                ) {
                    for (j in 0 until word.length - 1) {
                        if (word[j] in letters && word[j + 1] in exceptionsMistakes.keys) {
                            word = word.replace(
                                word[j] + word[j + 1].toString(),
                                word[j] + exceptionsMistakes[word[j + 1]].toString()
                            )
                            check = false
                        }
                    }
                } else if (Regex("""[^абвгдеёжзийклмнопрстуфхцчшщъыьэюя]""").replace(
                        word.toLowerCase(),
                        ""
                    ) !in exceptions
                ) {
                    for (j in 0 until word.length - 1) {
                        if (word[j] in letters && word[j + 1] in mistakes.keys) {
                            word = word.replace(
                                word[j] + word[j + 1].toString(),
                                word[j] + mistakes[word[j + 1]].toString()
                            )
                            check = false
                        }
                    }
                }
                if (check) {
                    it.write(splitLine[i])
                } else {
                    it.write(word)
                }
                if (i != splitLine.size - 1) it.write(" ")
            }
            it.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun findMaxLineLength(listOfStrings: List<String>): Int {
    var result = 0
    listOfStrings.forEach {
        if (it.trim().length > result) result = it.trim().length
    }
    return result
}

fun floorInt(x: Int): Int = floor(x.toDouble() / 2).toInt()

fun centerFile(inputName: String, outputName: String) {
    val maxLength = findMaxLineLength(File(inputName).readLines())
    var lineTrim: String
    File(outputName).bufferedWriter().use {
        for (line in File(inputName).readLines()) {
            if (line.isNotEmpty()) {
                lineTrim = line.trim()
                val ind = floorInt(maxLength - lineTrim.length)
                for (i in 0 until ind) {
                    it.write(" ")
                }
                it.write(lineTrim)
            } else {
                for (i in 0 until floorInt(maxLength)) {
                    it.write(" ")
                }
            }
            it.newLine()
        }
        it.close()
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между словами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val maxLength = findMaxLineLength(File(inputName).readLines())
    var wordsList: List<String>
    var wordsLength: Int
    File(outputName).bufferedWriter().use { itOutput ->
        for (line in File(inputName).readLines()) {
            wordsLength = 0
            if (line.replace(" ", "").isNotEmpty()) {
                wordsList = Regex("""\s+""").split(line.trim())
                wordsList.forEach { wordsLength += it.length }
                itOutput.write(wordsList[0])
                if (wordsList.size > 1) {
                    val counter = (maxLength - wordsLength) / (wordsList.size - 1).toDouble()
                    var exception = (maxLength - wordsLength) % (wordsList.size - 1)
                    for (i in 1 until wordsList.size) {
                        if (exception > 0) {
                            for (j in 0 until counter.toInt() + 1) itOutput.write(" ")
                            exception--
                        } else {
                            for (j in 0 until counter.toInt()) itOutput.write(" ")
                        }
                        itOutput.write(wordsList[i])
                    }
                }
            }
            itOutput.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    for (line in File(inputName).readLines()) {
        if (line.isNotEmpty()) {
            (Regex("""[^A-zА-яёЁ]+""")).split(line).filter { it.isNotEmpty() }
                .map { it.toLowerCase() }.forEach {
                    if (it in result.keys) {
                        result[it] = result[it]!! + 1
                    } else {
                        result[it] = 1
                    }
                }
        }
    }
    return result.entries.sortedByDescending { (_, value) -> value }.take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val names = mutableListOf<String>()
    val numbers = mutableListOf<Int>()
    File(inputName).readLines().forEach {
        if (it.length == it.toLowerCase().toSet().size) {
            names.add(it)
            numbers.add(it.toLowerCase().toSet().size)
        }
    }
    val maxNum = numbers.max()
    val result = mutableListOf<String>()
    for (i in 0 until names.size) {
        if (numbers[i] == maxNum) result.add(names[i])
    }
    outputStream.write(result.joinToString(", "))
    outputStream.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
    /*
    val outputStream = File(outputName).bufferedWriter()
    val replacement = mapOf(
        "**" to Pair("<b>", "</b>"),
        "*" to Pair("<i>", "</i>"),
        "~~" to Pair("<s>", "</s>")
    )
    val stackBI = Stack<String>()
    var checkBI: Boolean
    var modLine: String
    outputStream.write("<html><body><p>")
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.write("</p><p>")
        } else {
            modLine = line
            checkBI = false
            for ((key, value) in replacement) {
                while (key in modLine) {
                    checkBI = true
                    if (stackBI.isNotEmpty() && key == stackBI.peek()) {
                        modLine = modLine.replaceFirst(key, value.second)
                        stackBI.pop()
                    } else {
                        modLine = modLine.replaceFirst(key, value.first)
                        stackBI.push(key)
                    }
                }
            }
            if (checkBI) outputStream.write(modLine)
            else outputStream.write(line)
        }
    }
    outputStream.write("</p></body></html>")
    outputStream.close()
    */
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */

fun intToList(hv: Int): MutableList<Int> { //число в лист цифр
    var number = hv
    val result = mutableListOf<Int>()
    while (number != 0) {
        result.add(0, number % 10)
        number /= 10
    }
    return result
}

fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val listLhv = intToList(lhv) //список цифр делимого
    var divLhv = 0 //делимое
    var lenDivLhv: Int //длина делимого
    var modulo: Int //остаток от деления
    var subtrahend: Int //вычитаемое
    var counter = 0 //счетчик по элементам listLhv
    var indent = "" //отступ
    var start: String // "lhv | rhv"
    var plus = 0
    File(outputName).bufferedWriter().use {
        while (divLhv < rhv) {
            divLhv = divLhv * 10 + listLhv[counter]
            counter++
            if (counter == listLhv.size) break
        }
        lenDivLhv = "$divLhv".length
        modulo = divLhv % rhv
        subtrahend = divLhv - modulo
        if ("$subtrahend".length != lenDivLhv) {
            start = "$lhv | $rhv"
        } else {
            start = " $lhv | $rhv"
            plus++
        }
        it.write(start + "\n")
        for (j in 0 until lenDivLhv - "-$subtrahend".length) {
            it.write(" ")
        }
        it.write("-$subtrahend")
        for (j in 0 until start.length - lenDivLhv - "$rhv".length - plus) {
            it.write(" ")
        }
        it.write((lhv / rhv).toString() + "\n")
        for (j in 0 until max(lenDivLhv, "-$subtrahend".length)) {
            it.write("-")
        }
        it.newLine()
        for (i in 0 until lenDivLhv - "$modulo".length + plus) {
            indent += " "
        }
        while (counter < listLhv.size) {
            indent = ""
            for (j in 0 until counter - "$modulo".length + plus) {
                indent += " "
            }
            divLhv = modulo * 10 + listLhv[counter]
            subtrahend = divLhv - divLhv % rhv
            lenDivLhv = if (modulo == 0) {
                it.write(indent + "0$divLhv\n")
                "0$divLhv".length
            } else {
                it.write(indent + "$divLhv\n")
                "$divLhv".length
            }
            modulo = divLhv % rhv
            if (lenDivLhv == "$subtrahend".length) {
                indent = indent.substring(1)
            } else if (lenDivLhv > "$subtrahend".length) {
                for (j in 0 until lenDivLhv - "-$subtrahend".length) {
                    it.write(" ")
                }
            }
            it.write("$indent-$subtrahend\n$indent")
            for (j in 0 until max(lenDivLhv, "-$subtrahend".length)) {
                it.write("-")
            }
            for (j in 0 until max(lenDivLhv, "-$subtrahend".length) - "$modulo".length) {
                indent += " "
            }
            counter++
            it.newLine()
        }
        it.write("$indent$modulo")
    }
}