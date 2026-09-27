# Как открыть и сдать проект

## 1. Распакуй весь архив

Работай в папке `Bridge_Pattern_Assignment`, где лежат `README.md`, `src` и `run.bat`.
В архиве сохранена локальная история Git. Не удаляй папку `.git`, чтобы сохранить
существующие коммиты при публикации. Повторный `git init` не нужен.

## 2. Запусти на Windows

Нужен именно JDK 17 или новее, а не только JRE. Открой терминал в папке проекта:

```powershell
java -version
javac -version
.\run.bat
.\test.bat
```

Последняя команда должна вывести `PASS: 9 test groups, 41 checks.`
Если `javac` не найден, настрой JDK в PATH либо запускай из IntelliJ IDEA.

В IntelliJ IDEA открой папку проекта, выбери JDK 17 для проекта. Если IDE не
распознала папки автоматически, пометь `src` как Sources Root, а `test` как
Test Sources Root. Запускай `Main.main()` из `src/bridge/Main.java`.

## 3. Посмотри материалы

- `docs/Bridge_Pattern_Report.docx` — редактируемый отчёт на английском.
- `docs/Bridge_Pattern_Report.pdf` — тот же отчёт в PDF.
- `docs/Defense_Guide_RU.pdf` — объяснение кода, речь и вопросы к защите.
- `docs/bridge-uml.png` — схема, также встроенная в отчёт и README.

В отчёте указано имя Saule Jumabay. Проверь написание перед сдачей.
Поле `[INSERT YOUR REPOSITORY URL]` на последней странице нужно заменить
на настоящую ссылку после публикации. Затем заново экспортируй отчёт в PDF.

## 4. Опубликуй на GitHub с сохранением истории

Создай на GitHub пустой репозиторий, например `bridge-pattern-assignment3`.
Не добавляй при создании README, .gitignore или лицензию: файлы уже есть локально.
В терминале папки проекта выполни команды, заменив `YOUR_USERNAME` своим логином:

```sh
git log --oneline
git remote add origin https://github.com/YOUR_USERNAME/bridge-pattern-assignment3.git
git push -u origin main
```

При запросе авторизации войди в свой GitHub-аккаунт.
Для последующих коммитов настрой собственные данные в этом репозитории:

```sh
git config user.name "Saule Jumabay"
git config user.email "YOUR_GITHUB_EMAIL"
```

После вставки ссылки в DOCX и повторного экспорта PDF сохрани обновление:

```sh
git add docs/Bridge_Pattern_Report.docx docs/Bridge_Pattern_Report.pdf
git commit -m "Add repository URL to report"
git push
```

Если `origin` уже есть, проверь адрес через `git remote -v`; для исправления
используй `git remote set-url origin` с настоящим адресом своего репозитория.
Загрузка файлов через веб-форму GitHub не переносит эту локальную историю:
для сохранения коммитов используй `git push`.

Справка GitHub: https://docs.github.com/en/migrations/importing-source-code/using-the-command-line-to-import-source-code/adding-locally-hosted-code-to-github

## 5. Сдай в Moodle

Загрузи ссылку на GitHub и отчёт. Проверь, что преподаватель может открыть
репозиторий и что в отчёте больше нет поля-заглушки.

По инструкции срок — воскресенье, 23:59 в конце 4-й учебной недели;
защита — последнее практическое занятие 5-й недели. Точную дату смотри в Moodle.
Оценивание: код 40%, отчёт 10%, защита 50%.

## 6. Подготовь демонстрацию

Запусти программу; покажи вывод до и после переключения. Затем открой `Shape`
и покажи поле `Renderer`, метод `setRenderer` и вызов через интерфейс.
Объясни, почему `Square` рисует четыре линии и почему `Main` создаёт конкретные
отрисовщики, а сами фигуры от них не зависят. Прочитай вопросы в пособии и
потренируйся отвечать своими словами.
