const apiUrl = "/api/questions/student";
const submitUrl = "/api/questions/submit";

let questions = [];
let currentIndex = 0;
let currentQuestion = null;
let score = 0;
let answered = false;

async function loadQuestions() {
    const questionText = document.getElementById("question-text");
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error("Could not load questions.");
        questions = await response.json();

        if (questions.length === 0) {
            questionText.textContent = "No questions available.";
            document.getElementById("submit-button").disabled = true;
            return;
        }
        showQuestion();
    } catch (error) {
        questionText.textContent = error.message;
        console.error(error);
    }
}

function showQuestion() {
    currentQuestion = questions[currentIndex];
    answered = false;

    const text =
        currentQuestion.questionText ||
        currentQuestion.QuestionText ||
        currentQuestion.Questiontext ||
        "Question text unavailable";

    document.getElementById("question-text").textContent =
        "Question " + (currentIndex + 1) + " of " + questions.length +
        ": " + text;
    document.getElementById("labelA").textContent = currentQuestion.optionA;
    document.getElementById("labelB").textContent = currentQuestion.optionB;
    document.getElementById("labelC").textContent = currentQuestion.optionC;
    document.getElementById("labelD").textContent = currentQuestion.optionD;

    document.querySelectorAll('input[name="answer"]')
        .forEach(input => input.checked = false);
    document.getElementById("result").textContent = "";
    document.getElementById("submit-button").textContent = "Submit Answer";
    document.getElementById("submit-button").disabled = false;
}

document.getElementById("answer-form").addEventListener(
    "submit",
    async function (event) {
        event.preventDefault();

        // If the answer was already checked, this click means "Next"
        if (answered) {
            currentIndex++;
            if (currentIndex < questions.length) {
                showQuestion();
            } else {
                document.getElementById("question-container").innerHTML =
                    "<h2>Exam finished!</h2><p>Your score: " + score +
                    " / " + questions.length + "</p>";
            }
            return;
        }

        const selected = document.querySelector('input[name="answer"]:checked');
        const result = document.getElementById("result");
        const submitButton = document.getElementById("submit-button");

        if (!selected) {
            result.textContent = "Please select an answer first.";
            return;
        }

        submitButton.disabled = true;
        result.textContent = "Checking your answer...";

        try {
            const response = await fetch(submitUrl, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    questionId: currentQuestion.id,
                    selectedOption: selected.value
                })
            });

            const data = await response.json();
            if (!response.ok) {
                throw new Error(data.message || data.error || "Could not submit answer.");
            }

            result.textContent = data.message + (data.correct ? " 🎉" : " Wrong answer.");
            if (data.correct) score++;

            answered = true;
            submitButton.textContent =
                currentIndex < questions.length - 1 ? "Next Question" : "Finish";
        } catch (error) {
            result.textContent = "Error: " + error.message;
            console.error(error);
        } finally {
            submitButton.disabled = false;
        }
    }
);

loadQuestions();