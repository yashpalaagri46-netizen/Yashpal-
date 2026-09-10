/* =========================================================
   STUDY WALLAH
   Main JavaScript
   NEET + JEE Preparation Platform
   Created by Yashpal Aagri
   ========================================================= */

"use strict";

/* =========================
   BASIC HELPERS
========================= */

const $ = (selector, parent = document) => parent.querySelector(selector);
const $$ = (selector, parent = document) =>
  [...parent.querySelectorAll(selector)];

const STORAGE_KEY = "study_wallah_data_v1";

function escapeHTML(value) {
  return String(value ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}

function loadData() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE_KEY)) || {};
  } catch {
    return {};
  }
}

function saveData() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
}

function clamp(number, min, max) {
  return Math.min(Math.max(number, min), max);
}

/* =========================
   DEFAULT STATE
========================= */

const defaultState = {
  profile: {
    name: "Yashpal Aagri",
    exam: "NEET",
    language: "English",
    avatar: "👨‍🎓"
  },

  theme: "green",

  bookmarks: [],

  progress: {
    overall: 0,
    physics: 0,
    chemistry: 0,
    biology: 0,
    mathematics: 0
  },

  activity: [],

  planner: [],

  quizHistory: [],

  testHistory: [],

  notifications: [
    {
      id: Date.now(),
      title: "Welcome to Study Wallah",
      text: "Your NEET + JEE preparation journey starts here.",
      time: "Just now",
      read: false
    }
  ],

  settings: {
    studyReminder: true,
    testReminder: true
  }
};

const savedState = loadData();

const state = {
  ...defaultState,
  ...savedState,

  profile: {
    ...defaultState.profile,
    ...(savedState.profile || {})
  },

  progress: {
    ...defaultState.progress,
    ...(savedState.progress || {})
  },

  settings: {
    ...defaultState.settings,
    ...(savedState.settings || {})
  }
};

/* =========================
   QUESTION DATA
========================= */

const questionBank = [
  {
    id: "bio-1",
    exam: "NEET",
    subject: "Biology",
    chapter: "Cell",
    difficulty: "Easy",
    question: "Which organelle is known as the powerhouse of the cell?",
    options: [
      "Nucleus",
      "Mitochondria",
      "Ribosome",
      "Golgi apparatus"
    ],
    answer: 1,
    explanation:
      "Mitochondria produce most of the ATP used by the cell."
  },

  {
    id: "bio-2",
    exam: "NEET",
    subject: "Biology",
    chapter: "Biomolecules",
    difficulty: "Easy",
    question: "Which molecule carries genetic information in most organisms?",
    options: [
      "DNA",
      "Glucose",
      "ATP",
      "Cholesterol"
    ],
    answer: 0,
    explanation:
      "DNA stores hereditary genetic information in most organisms."
  },

  {
    id: "bio-3",
    exam: "NEET",
    subject: "Biology",
    chapter: "Human Physiology",
    difficulty: "Medium",
    question: "Which blood cells are primarily responsible for oxygen transport?",
    options: [
      "Platelets",
      "Leukocytes",
      "Erythrocytes",
      "Lymphocytes"
    ],
    answer: 2,
    explanation:
      "Erythrocytes contain haemoglobin, which binds and transports oxygen."
  },

  {
    id: "phy-1",
    exam: "NEET",
    subject: "Physics",
    chapter: "Units and Measurements",
    difficulty: "Easy",
    question: "What is the SI unit of force?",
    options: [
      "Joule",
      "Watt",
      "Newton",
      "Pascal"
    ],
    answer: 2,
    explanation:
      "The SI unit of force is the newton (N)."
  },

  {
    id: "phy-2",
    exam: "JEE",
    subject: "Physics",
    chapter: "Kinematics",
    difficulty: "Medium",
    question:
      "For uniformly accelerated motion, which quantity remains constant?",
    options: [
      "Acceleration",
      "Velocity",
      "Displacement",
      "Distance"
    ],
    answer: 0,
    explanation:
      "Uniform acceleration means acceleration remains constant with time."
  },

  {
    id: "chem-1",
    exam: "NEET",
    subject: "Chemistry",
    chapter: "Atomic Structure",
    difficulty: "Easy",
    question: "What is the charge of an electron?",
    options: [
      "Positive",
      "Negative",
      "Neutral",
      "Variable"
    ],
    answer: 1,
    explanation:
      "An electron carries one unit of negative electric charge."
  },

  {
    id: "chem-2",
    exam: "JEE",
    subject: "Chemistry",
    chapter: "Mole Concept",
    difficulty: "Medium",
    question:
      "Approximately how many particles are present in one mole of a substance?",
    options: [
      "6.022 × 10²³",
      "3.011 × 10²³",
      "9.81 × 10²",
      "1.602 × 10⁻¹⁹"
    ],
    answer: 0,
    explanation:
      "One mole contains Avogadro's number, approximately 6.022 × 10²³ particles."
  },

  {
    id: "math-1",
    exam: "JEE",
    subject: "Mathematics",
    chapter: "Algebra",
    difficulty: "Easy",
    question: "If x + 5 = 12, what is x?",
    options: [
      "5",
      "6",
      "7",
      "8"
    ],
    answer: 2,
    explanation:
      "Subtracting 5 from both sides gives x = 7."
  },

  {
    id: "math-2",
    exam: "JEE",
    subject: "Mathematics",
    chapter: "Quadratic Equations",
    difficulty: "Medium",
    question:
      "What is the discriminant of ax² + bx + c = 0?",
    options: [
      "b² + 4ac",
      "b² − 4ac",
      "a² − 4bc",
      "4ac − b²"
    ],
    answer: 1,
    explanation:
      "The discriminant is D = b² − 4ac."
  }
];

/* =========================
   DPP DATA
========================= */

const dppQuestions = [
  ...questionBank.slice(0, 6)
];

/* =========================
   COURSE DATA
========================= */

const courses = [
  {
    id: "neet-physics",
    exam: "NEET",
    subject: "Physics",
    title: "NEET Physics",
    icon: "⚡",
    description: "Concepts, formulas, numericals and NEET practice.",
    chapters: 20,
    progress: 0
  },

  {
    id: "neet-chemistry",
    exam: "NEET",
    subject: "Chemistry",
    title: "NEET Chemistry",
    icon: "🧪",
    description: "Physical, Organic and Inorganic Chemistry.",
    chapters: 30,
    progress: 0
  },

  {
    id: "neet-biology",
    exam: "NEET",
    subject: "Biology",
    title: "NEET Biology",
    icon: "🧬",
    description: "Botany + Zoology with NCERT-focused preparation.",
    chapters: 32,
    progress: 0
  },

  {
    id: "jee-physics",
    exam: "JEE",
    subject: "Physics",
    title: "JEE Physics",
    icon: "🚀",
    description: "JEE Main + Advanced Physics preparation.",
    chapters: 25,
    progress: 0
  },

  {
    id: "jee-chemistry",
    exam: "JEE",
    subject: "Chemistry",
    title: "JEE Chemistry",
    icon: "⚗️",
    description: "Complete JEE Chemistry preparation.",
    chapters: 30,
    progress: 0
  },

  {
    id: "jee-mathematics",
    exam: "JEE",
    subject: "Mathematics",
    title: "JEE Mathematics",
    icon: "📐",
    description: "Algebra, Calculus, Coordinate Geometry and more.",
    chapters: 28,
    progress: 0
  }
];

/* =========================
   GLOBAL APP STATE
========================= */

let quizState = null;
let quizTimer = null;
let testTimer = null;

let currentQuizSeconds = 0;
let currentTestSeconds = 0;

/* =========================
   TOAST
========================= */

function showToast(message, icon = "✓") {
  const toast = $("#toast");
  const toastMessage = $("#toastMessage");
  const toastIcon = $("#toastIcon");

  if (!toast) return;

  if (toastMessage) {
    toastMessage.textContent = message;
  }

  if (toastIcon) {
    toastIcon.textContent = icon;
  }

  toast.classList.add("show");

  clearTimeout(showToast.timer);

  showToast.timer = setTimeout(() => {
    toast.classList.remove("show");
  }, 2600);
}

/* =========================
   MODAL
========================= */

function openModal(content) {
  const modal = $("#modal");
  const modalContent = $("#modalContent");

  if (!modal || !modalContent) return;

  modalContent.innerHTML = content;
  modal.classList.add("show");
  document.body.classList.add("modal-open");
}

function closeModal() {
  const modal = $("#modal");

  if (!modal) return;

  modal.classList.remove("show");
  document.body.classList.remove("modal-open");
}

function setupModal() {
  $("#modalClose")?.addEventListener("click", closeModal);

  $("#modal")?.addEventListener("click", (event) => {
    if (event.target.classList.contains("modal-backdrop")) {
      closeModal();
    }
  });
}

/* =========================
   LOADER
========================= */

function hideLoader() {
  const loader = $("#appLoader");

  if (!loader) return;

  setTimeout(() => {
    loader.classList.add("hidden");

    setTimeout(() => {
      loader.style.display = "none";
    }, 500);
  }, 700);
}

/* =========================
   PROFILE
========================= */

function getStudentName() {
  return state.profile.name?.trim() || "Student";
}

function updateProfileUI() {
  const name = getStudentName();

  const elements = [
    "#sidebarStudentName",
    "#topStudentName",
    "#profileDisplayName",
    "#heroStudentName"
  ];

  elements.forEach((selector) => {
    const element = $(selector);

    if (element) {
      element.textContent = name;
    }
  });

  const avatarElements = [
    "#topProfileAvatar",
    "#profileAvatar"
  ];

  avatarElements.forEach((selector) => {
    const element = $(selector);

    if (element) {
      element.textContent = state.profile.avatar || "👨‍🎓";
    }
  });

  const profileNameInput = $("#profileNameInput");

  if (profileNameInput) {
    profileNameInput.value = name;
  }

  const profileExam = $("#profileExam");

  if (profileExam) {
    profileExam.value = state.profile.exam || "NEET";
  }
}

/* =========================
   PROGRESS
========================= */

function calculateOverallProgress() {
  const values = [
    Number(state.progress.physics) || 0,
    Number(state.progress.chemistry) || 0,
    Number(state.progress.biology) || 0,
    Number(state.progress.mathematics) || 0
  ];

  const activeValues = values.filter((value) => value > 0);

  if (!activeValues.length) {
    return Number(state.progress.overall) || 0;
  }

  return Math.round(
    activeValues.reduce((sum, value) => sum + value, 0) /
      activeValues.length
  );
}

function updateProgressUI() {
  const overall = calculateOverallProgress();

  state.progress.overall = overall;

  const overallElements = [
    "#overallProgress",
    "#plannerProgress"
  ];

  overallElements.forEach((selector) => {
    const element = $(selector);

    if (element) {
      element.textContent = `${overall}%`;
    }
  });

  const sidebarText = $("#sidebarProgressText");

  if (sidebarText) {
    sidebarText.textContent = `${overall}%`;
  }

  const sidebarBar = $("#sidebarProgressBar");

  if (sidebarBar) {
    sidebarBar.style.width = `${overall}%`;
  }

  saveData();
}

/* =========================
   ACTIVITY
========================= */

function addActivity(title, description = "") {
  state.activity.unshift({
    id: Date.now(),
    title,
    description,
    time: new Date().toLocaleString()
  });

  state.activity = state.activity.slice(0, 20);

  saveData();
  renderRecentActivity();
}

function renderRecentActivity() {
  const container = $("#recentActivity");

  if (!container) return;

  if (!state.activity.length) {
    container.innerHTML = `
      <div class="empty-state">
        <div class="empty-icon">📚</div>
        <h3>No recent activity</h3>
        <p>Start studying to see your activity here.</p>
      </div>
    `;
    return;
  }

  container.innerHTML = `
    <div class="activity-list">
      ${state.activity
        .slice(0, 8)
        .map(
          (item) => `
            <div class="activity-item">
              <div class="activity-icon">✓</div>
              <div>
                <strong>${escapeHTML(item.title)}</strong>
                <p>${escapeHTML(item.description || "")}</p>
                <small>${escapeHTML(item.time)}</small>
              </div>
            </div>
          `
        )
        .join("")}
    </div>
  `;
}

/* =========================
   NAVIGATION
========================= */

function showPage(pageName) {
  if (!pageName) return;

  const pages = $$(".page");

  pages.forEach((page) => {
    page.classList.remove("active");
  });

  const target = $(`#page-${pageName}`);

  if (target) {
    target.classList.add("active");
  }

  $$("[data-page]").forEach((item) => {
    item.classList.toggle(
      "active",
      item.dataset.page === pageName
    );
  });

  document.body.classList.remove("sidebar-open");

  const overlay = $("#mobileOverlay");

  if (overlay) {
    overlay.classList.remove("show");
  }

  window.scrollTo({
    top: 0,
    behavior: "smooth"
  });

  if (pageName === "courses") renderCourses();
  if (pageName === "dpp") renderDPP();
  if (pageName === "question-bank") renderQuestionBank();
  if (pageName === "bookmarks") renderBookmarks();
  if (pageName === "analytics") renderAnalytics();
  if (pageName === "planner") renderPlanner();
  if (pageName === "notes") renderNotes();
  if (pageName === "youtube") setupYouTubePage();
  if (pageName === "profile") updateProfileUI();
  if (pageName === "settings") renderThemeGrid();

  closeMobileMenu();
}

function setupNavigation() {
  $$("[data-page]").forEach((item) => {
    item.addEventListener("click", () => {
      showPage(item.dataset.page);
    });
  });
}

/* =========================
   MOBILE MENU
========================= */

function openMobileMenu() {
  document.body.classList.add("sidebar-open");

  const overlay = $("#mobileOverlay");

  if (overlay) {
    overlay.classList.add("show");
  }
}

function closeMobileMenu() {
  document.body.classList.remove("sidebar-open");

  const overlay = $("#mobileOverlay");

  if (overlay) {
    overlay.classList.remove("show");
  }
}

function setupMobileMenu() {
  $("#menuBtn")?.addEventListener("click", openMobileMenu);
  $("#mobileOverlay")?.addEventListener("click", closeMobileMenu);
}

/* =========================
   COURSES
========================= */

function renderCourses() {
  const container = $(".course-grid");

  if (!container) return;

  container.innerHTML = courses
    .map((course) => {
      const progress =
        Number(state.progress[course.subject.toLowerCase()]) || 0;

      return `
        <article class="course-card">
          <div class="course-cover ${course.subject.toLowerCase()}">
            <span class="course-symbol">${course.icon}</span>
          </div>

          <div class="course-body">
            <span class="course-category">
              ${course.exam} • ${course.subject}
            </span>

            <h3>${escapeHTML(course.title)}</h3>

            <p>${escapeHTML(course.description)}</p>

            <div class="course-meta">
              <span>📚 ${course.chapters} Chapters</span>
              <span>🎯 Beginner → Advanced</span>
            </div>

            <div class="course-progress">
              <div class="progress-line">
                <span style="width:${progress}%"></span>
              </div>
              <small>${progress}% complete</small>
            </div>

            <button
              class="course-btn"
              data-course="${course.id}"
            >
              ${progress > 0 ? "Continue Learning" : "Start Course"}
            </button>
          </div>
        </article>
      `;
    })
    .join("");

  $$(".course-btn").forEach((button) => {
    button.addEventListener("click", () => {
      const course = courses.find(
        (item) => item.id === button.dataset.course
      );

      if (!course) return;

      const key = course.subject.toLowerCase();

      state.progress[key] = clamp(
        (Number(state.progress[key]) || 0) + 5,
        0,
        100
      );

      addActivity(
        `Started ${course.title}`,
        "Course progress updated."
      );

      updateProgressUI();

      showToast(`${course.title} opened`, "📚");

      openModal(`
        <div class="modal-icon">${course.icon}</div>
        <h2>${escapeHTML(course.title)}</h2>
        <p>${escapeHTML(course.description)}</p>

        <div class="modal-info-grid">
          <div><strong>${course.chapters}</strong><span>Chapters</span></div>
          <div><strong>${course.exam}</strong><span>Exam</span></div>
          <div><strong>100%</strong><span>Goal</span></div>
        </div>

        <button class="primary-btn" id="modalStartCourse">
          Start Learning
        </button>
      `);

      $("#modalStartCourse")?.addEventListener("click", () => {
        closeModal();
        showToast("Learning session started", "🚀");
      });
    });
  });
}

/* =========================
   DPP
========================= */

function renderDPP() {
  const container = $("#page-dpp .question-list");

  if (!container) return;

  const exam = $("#dppExamFilter")?.value || "ALL";
  const subject = $("#dppSubjectFilter")?.value || "ALL";
  const difficulty = $("#dppDifficulty")?.value || "ALL";

  const filtered = dppQuestions.filter((question) => {
    const examMatch = exam === "ALL" || question.exam === exam;
    const subjectMatch =
      subject === "ALL" || question.subject === subject;
    const difficultyMatch =
      difficulty === "ALL" ||
      question.difficulty === difficulty;

    return examMatch && subjectMatch && difficultyMatch;
  });

  if (!filtered.length) {
    container.innerHTML = `
      <div class="empty-state">
        <div class="empty-icon">📝</div>
        <h3>No DPP found</h3>
        <p>Try another filter.</p>
      </div>
    `;
    return;
  }

  container.innerHTML = filtered
    .map(
      (question, index) => `
        <article class="question-card">
          <div class="question-top">
            <span class="question-number">Q${index + 1}</span>
            <span class="difficulty ${question.difficulty.toLowerCase()}">
              ${question.difficulty}
            </span>
            <span class="question-subject">
              ${question.exam} • ${question.subject}
            </span>
          </div>

          <h3>${escapeHTML(question.question)}</h3>

          <div class="options">
            ${question.options
              .map(
                (option, optionIndex) => `
                  <button
                    class="question-option"
                    data-question="${question.id}"
                    data-option="${optionIndex}"
                  >
                    <span>${String.fromCharCode(65 + optionIndex)}</span>
                    ${escapeHTML(option)}
                  </button>
                `
              )
              .join("")}
          </div>

          <button
            class="explanation-btn"
            data-explanation="${question.id}"
          >
            💡 Show Explanation
          </button>

          <div class="question-explanation" id="explanation-${question.id}">
            ${escapeHTML(question.explanation)}
          </div>
        </article>
      `
    )
    .join("");

  setupQuestionInteractions(container);
}

/* =========================
   QUESTION INTERACTIONS
========================= */

function setupQuestionInteractions(container) {
  container.querySelectorAll(".question-option").forEach((button) => {
    button.addEventListener("click", () => {
      const question = questionBank.find(
        (item) => item.id === button.dataset.question
      );

      if (!question) return;

      const selected = Number(button.dataset.option);

      const buttons = container.querySelectorAll(
        `[data-question="${question.id}"]`
      );

      buttons.forEach((item) => {
        item.disabled = true;

        if (Number(item.dataset.option) === question.answer) {
          item.classList.add("correct");
        }
      });

      if (selected !== question.answer) {
        button.classList.add("incorrect");
      }

      addActivity(
        selected === question.answer
          ? "Question answered correctly"
          : "Question attempted",
        `${question.subject} • ${question.chapter}`
      );

      showToast(
        selected === question.answer
          ? "Correct answer! 🎉"
          : "Keep learning and try again.",
        selected === question.answer ? "✓" : "📚"
      );
    });
  });

  container
    .querySelectorAll(".explanation-btn")
    .forEach((button) => {
      button.addEventListener("click", () => {
        const explanation = $(
          `#explanation-${button.dataset.explanation}`
        );

        if (explanation) {
          explanation.classList.toggle("show");
        }
      });
    });
}

/* =========================
   QUESTION BANK
========================= */

function renderQuestionBank() {
  const container = $("#questionBankContainer");

  if (!container) return;

  container.innerHTML = `
    <div class="question-list">
      ${questionBank
        .map(
          (question, index) => `
            <article class="question-card">
              <div class="question-top">
                <span class="question-number">Q${index + 1}</span>
                <span class="difficulty ${question.difficulty.toLowerCase()}">
                  ${question.difficulty}
                </span>
                <span class="question-subject">
                  ${question.exam} • ${question.subject}
                </span>
              </div>

              <h3>${escapeHTML(question.question)}</h3>

              <div class="options">
                ${question.options
                  .map(
                    (option, optionIndex) => `
                      <button
                        class="question-option qb-option"
                        data-question="${question.id}"
                        data-option="${optionIndex}"
                      >
                        <span>${String.fromCharCode(65 + optionIndex)}</span>
                        ${escapeHTML(option)}
                      </button>
                    `
                  )
                  .join("")}
              </div>

              <div class="question-actions">
                <button
                  class="explanation-btn"
                  data-explanation="${question.id}"
                >
                  💡 Explanation
                </button>

                <button
                  class="bookmark-btn"
                  data-bookmark="${question.id}"
                >
                  🔖 Bookmark
                </button>
              </div>

              <div
                class="question-explanation"
                id="explanation-${question.id}"
              >
                ${escapeHTML(question.explanation)}
              </div>
            </article>
          `
        )
        .join("")}
    </div>
  `;

  setupQuestionInteractions(container);
  setupBookmarkButtons(container);
}

/* =========================
   BOOKMARKS
========================= */

function isBookmarked(id) {
  return state.bookmarks.includes(id);
}

function toggleBookmark(id) {
  if (isBookmarked(id)) {
    state.bookmarks = state.bookmarks.filter(
      (bookmark) => bookmark !== id
    );

    showToast("Removed from bookmarks", "🔖");
  } else {
    state.bookmarks.push(id);

    showToast("Added to bookmarks", "🔖");
  }

  saveData();
  updateBookmarkCount();
  renderBookmarks();
}

function setupBookmarkButtons(parent = document) {
  parent.querySelectorAll("[data-bookmark]").forEach((button) => {
    button.addEventListener("click", () => {
      toggleBookmark(button.dataset.bookmark);

      button.classList.toggle(
        "active",
        isBookmarked(button.dataset.bookmark)
      );
    });

    button.classList.toggle(
      "active",
      isBookmarked(button.dataset.bookmark)
    );
  });
}

function updateBookmarkCount() {
  const count = state.bookmarks.length;

  $$(".bookmark-count").forEach((element) => {
    element.textContent = count;
  });
}

function renderBookmarks() {
  const container = $("#bookmarksContainer");

  if (!container) return;

  const savedQuestions = questionBank.filter((question) =>
    state.bookmarks.includes(question.id)
  );

  if (!savedQuestions.length) {
    container.innerHTML = `
      <div class="empty-state">
        <div class="empty-icon">🔖</div>
        <h3>No bookmarks yet</h3>
        <p>Bookmark questions, notes and resources for quick access.</p>
      </div>
    `;
    return;
  }

  container.innerHTML = `
    <div class="bookmark-grid">
      ${savedQuestions
        .map(
          (question) => `
            <article class="question-card">
              <div class="question-top">
                <span class="question-number">
                  ${question.subject}
                </span>

                <span class="difficulty ${question.difficulty.toLowerCase()}">
                  ${question.difficulty}
                </span>
              </div>

              <h3>${escapeHTML(question.question)}</h3>

              <p>${escapeHTML(question.explanation)}</p>

              <button
                class="bookmark-btn active"
                data-bookmark="${question.id}"
              >
                🔖 Remove Bookmark
              </button>
            </article>
          `
        )
        .join("")}
    </div>
  `;

  setupBookmarkButtons(container);
}

/* =========================
   QUIZ
========================= */

function setupQuiz() {
  $("#startQuizBtn")?.addEventListener("click", startQuiz);
}

function startQuiz() {
  const exam = $("#quizExam")?.value || "NEET";
  const subject = $("#quizSubject")?.value || "ALL";
  const count = Number($("#quizCount")?.value || 5);

  let available = questionBank.filter(
    (question) =>
      question.exam === exam &&
      (subject === "ALL" || question.subject === subject)
  );

  if (!available.length) {
    available = questionBank.filter(
      (question) => question.exam === exam
    );
  }

  if (!available.length) {
    showToast("Questions not available yet.", "⚠️");
    return;
  }

  available = [...available]
    .sort(() => Math.random() - 0.5)
    .slice(0, count);

  quizState = {
    exam,
    subject,
    questions: available,
    current: 0,
    answers: {},
    score: 0
  };

  currentQuizSeconds = Math.max(available.length * 60, 60);

  renderQuizQuestion();
  startQuizTimer();

  addActivity(
    `${exam} Quiz Started`,
    `${available.length} questions`
  );
}

function renderQuizQuestion() {
  const container = $("#quizContainer");

  if (!container || !quizState) return;

  const question = quizState.questions[quizState.current];

  if (!question) {
    finishQuiz();
    return;
  }

  container.innerHTML = `
    <div class="quiz-question-card">
      <div class="quiz-progress">
        <span>
          Question ${quizState.current + 1}
          / ${quizState.questions.length}
        </span>

        <span id="quizTimer">
          ${formatTime(currentQuizSeconds)}
        </span>
      </div>

      <div class="question-top">
        <span class="question-number">
          Q${quizState.current + 1}
        </span>

        <span class="question-subject">
          ${question.subject}
        </span>
      </div>

      <h2>${escapeHTML(question.question)}</h2>

      <div class="options quiz-options">
        ${question.options
          .map(
            (option, index) => `
              <button
                class="quiz-option"
                data-answer="${index}"
              >
                <span>${String.fromCharCode(65 + index)}</span>
                ${escapeHTML(option)}
              </button>
            `
          )
          .join("")}
      </div>

      <button class="primary-btn" id="nextQuizBtn">
        ${quizState.current === quizState.questions.length - 1
          ? "Finish Quiz"
          : "Next Question"}
      </button>
    </div>
  `;

  $$(".quiz-option", container).forEach((button) => {
    button.addEventListener("click", () => {
      $$(".quiz-option", container).forEach((item) => {
        item.classList.remove("selected");
      });

      button.classList.add("selected");

      quizState.answers[quizState.current] =
        Number(button.dataset.answer);
    });
  });

  $("#nextQuizBtn")?.addEventListener("click", () => {
    if (
      quizState.answers[quizState.current] === undefined
    ) {
      showToast("Please select an answer.", "⚠️");
      return;
    }

    quizState.current++;
    renderQuizQuestion();
  });
}

function startQuizTimer() {
  clearInterval(quizTimer);

  quizTimer = setInterval(() => {
    currentQuizSeconds--;

    const timer = $("#quizTimer");

    if (timer) {
      timer.textContent = formatTime(
        Math.max(currentQuizSeconds, 0)
      );
    }

    if (currentQuizSeconds <= 0) {
      clearInterval(quizTimer);
      finishQuiz();
    }
  }, 1000);
}

function finishQuiz() {
  clearInterval(quizTimer);

  if (!quizState) return;

  let correct = 0;

  quizState.questions.forEach((question, index) => {
    if (
      quizState.answers[index] === question.answer
    ) {
      correct++;
    }
  });

  const total = quizState.questions.length;

  const percentage = Math.round(
    (correct / total) * 100
  );

  state.quizHistory.unshift({
    id: Date.now(),
    exam: quizState.exam,
    subject: quizState.subject,
    total,
    correct,
    percentage,
    date: new Date().toLocaleString()
  });

  state.quizHistory = state.quizHistory.slice(0, 30);

  saveData();

  const container = $("#quizContainer");

  if (container) {
    container.innerHTML = `
      <div class="result-card">
        <div class="result-icon">🏆</div>

        <h2>Quiz Complete!</h2>

        <div class="result-score">
          ${percentage}%
        </div>

        <p>
          You answered
          <strong>${correct}</strong>
          out of
          <strong>${total}</strong>
          correctly.
        </p>

        <div class="result-actions">
          <button class="primary-btn" id="retryQuizBtn">
            Retry Quiz
          </button>

          <button class="secondary-btn" id="quizHomeBtn">
            Back to Quiz
          </button>
        </div>
      </div>
    `;

    $("#retryQuizBtn")?.addEventListener(
      "click",
      startQuiz
    );

    $("#quizHomeBtn")?.addEventListener(
      "click",
      () => {
        showPage("quiz");
      }
    );
  }

  addActivity(
    "Quiz completed",
    `${correct}/${total} correct • ${percentage}%`
  );

  quizState = null;

  showToast(
    `Quiz finished: ${percentage}%`,
    "🏆"
  );
}

/* =========================
   TESTS
========================= */

function setupTests() {
  $$(".test-card").forEach((card) => {
    card.addEventListener("click", () => {
      startTest(
        card.dataset.exam ||
          card.querySelector(".test-badge")?.textContent ||
          "NEET"
      );
    });
  });
}

function startTest(exam) {
  const questions = questionBank
    .filter((question) => question.exam === exam)
    .slice(0, 5);

  if (!questions.length) {
    showToast("Test content is being prepared.", "📚");
    return;
  }

  currentTestSeconds = questions.length * 90;

  openModal(`
    <div class="test-start-modal">
      <div class="modal-icon">📝</div>

      <h2>${escapeHTML(exam)} Mock Test</h2>

      <p>
        ${questions.length} questions are ready.
      </p>

      <div class="modal-info-grid">
        <div>
          <strong>${questions.length}</strong>
          <span>Questions</span>
        </div>

        <div>
          <strong>${formatTime(currentTestSeconds)}</strong>
          <span>Time</span>
        </div>

        <div>
          <strong>Auto</strong>
          <span>Result</span>
        </div>
      </div>

      <button class="primary-btn" id="beginTestBtn">
        Begin Test
      </button>
    </div>
  `);

  $("#beginTestBtn")?.addEventListener("click", () => {
    closeModal();

    runTest(questions, exam);
  });
}

function runTest(questions, exam) {
  let current = 0;
  let answers = {};

  clearInterval(testTimer);

  const renderTestQuestion = () => {
    openModal(`
      <div class="test-running">
        <div class="quiz-progress">
          <span>
            ${exam} Test • Question ${current + 1}/${questions.length}
          </span>

          <span id="testTimer">
            ${formatTime(currentTestSeconds)}
          </span>
        </div>

        <h2>
          ${escapeHTML(questions[current].question)}
        </h2>

        <div class="options">
          ${questions[current].options
            .map(
              (option, index) => `
                <button
                  class="quiz-option test-option"
                  data-index="${index}"
                >
                  <span>${String.fromCharCode(65 + index)}</span>
                  ${escapeHTML(option)}
                </button>
              `
            )
            .join("")}
        </div>

        <button class="primary-btn" id="testNextBtn">
          ${current === questions.length - 1
            ? "Submit Test"
            : "Next"}
        </button>
      </div>
    `);

    $$(".test-option").forEach((button) => {
      button.addEventListener("click", () => {
        $$(".test-option").forEach((item) =>
          item.classList.remove("selected")
        );

        button.classList.add("selected");

        answers[current] =
          Number(button.dataset.index);
      });
    });

    $("#testNextBtn")?.addEventListener("click", () => {
      if (answers[current] === undefined) {
        showToast("Select an answer first.", "⚠️");
        return;
      }

      if (current === questions.length - 1) {
        finishTest(questions, answers, exam);
        return;
      }

      current++;
      renderTestQuestion();
    });
  };

  testTimer = setInterval(() => {
    currentTestSeconds--;

    const timer = $("#testTimer");

    if (timer) {
      timer.textContent = formatTime(
        Math.max(currentTestSeconds, 0)
      );
    }

    if (currentTestSeconds <= 0) {
      clearInterval(testTimer);
      finishTest(questions, answers, exam);
    }
  }, 1000);

  renderTestQuestion();
}

function finishTest(questions, answers, exam) {
  clearInterval(testTimer);

  let correct = 0;

  questions.forEach((question, index) => {
    if (answers[index] === question.answer) {
      correct++;
    }
  });

  const percentage = Math.round(
    (correct / questions.length) * 100
  );

  state.testHistory.unshift({
    id: Date.now(),
    exam,
    correct,
    total: questions.length,
    percentage,
    date: new Date().toLocaleString()
  });

  saveData();

  openModal(`
    <div class="result-card">
      <div class="result-icon">🎯</div>

      <h2>${escapeHTML(exam)} Test Complete</h2>

      <div class="result-score">
        ${percentage}%
      </div>

      <p>
        ${correct} / ${questions.length} answers correct.
      </p>

      <button class="primary-btn" id="closeTestResult">
        Done
      </button>
    </div>
  `);

  $("#closeTestResult")?.addEventListener(
    "click",
    closeModal
  );

  addActivity(
    `${exam} Test completed`,
    `${percentage}% score`
  );
}

/* =========================
   NOTES
========================= */

const notes = [
  {
    title: "Physics Formula Revision",
    subject: "Physics",
    icon: "⚡",
    text: "Keep a separate formula sheet and revise it regularly."
  },
  {
    title: "Chemistry Quick Revision",
    subject: "Chemistry",
    icon: "🧪",
    text: "Focus on concepts, reactions and important exceptions."
  },
  {
    title: "Biology NCERT Notes",
    subject: "Biology",
    icon: "🧬",
    text: "Revise NCERT diagrams, definitions and key statements."
  },
  {
    title: "JEE Mathematics",
    subject: "Mathematics",
    icon: "📐",
    text: "Practice problems and analyse mistakes after every session."
  }
];

function renderNotes() {
  const container = $("#page-notes .notes-grid");

  if (!container) return;

  container.innerHTML = notes
    .map(
      (note) => `
        <article class="note-card">
          <div class="note-icon">${note.icon}</div>

          <span>${escapeHTML(note.subject)}</span>

          <h3>${escapeHTML(note.title)}</h3>

          <p>${escapeHTML(note.text)}</p>

          <button
            class="secondary-btn note-open"
            data-note="${escapeHTML(note.title)}"
          >
            Open Note
          </button>
        </article>
      `
    )
    .join("");

  $$(".note-open", container).forEach((button) => {
    button.addEventListener("click", () => {
      const note = notes.find(
        (item) => item.title === button.dataset.note
      );

      if (!note) return;

      openModal(`
        <div class="modal-icon">${note.icon}</div>

        <h2>${escapeHTML(note.title)}</h2>

        <p>${escapeHTML(note.text)}</p>

        <button class="primary-btn" id="saveNoteBtn">
          ✓ Mark as Read
        </button>
      `);

      $("#saveNoteBtn")?.addEventListener(
        "click",
        () => {
          addActivity(
            `Read note: ${note.title}`,
            note.subject
          );

          closeModal();

          showToast("Note marked as read", "✓");
        }
      );
    });
  });
}

/* =========================
   PLANNER
========================= */

function renderPlanner() {
  const container = $("#plannerTasks");

  if (!container) return;

  const total = state.planner.length;

  const completed = state.planner.filter(
    (task) => task.completed
  ).length;

  const streak = calculateStreak();

  $("#plannerTotal") &&
    ($("#plannerTotal").textContent = total);

  $("#plannerCompleted") &&
    ($("#plannerCompleted").textContent = completed);

  $("#plannerStreak") &&
    ($("#plannerStreak").textContent = streak);

  const progress =
    total > 0
      ? Math.round((completed / total) * 100)
      : 0;

  const progressElement = $("#plannerProgress");

  if (progressElement) {
    progressElement.textContent = `${progress}%`;
  }

  if (!state.planner.length) {
    container.innerHTML = `
      <div class="empty-state">
        <div class="empty-icon">📅</div>
        <h3>No study tasks yet</h3>
        <p>Add your first task and start your study plan.</p>
      </div>
    `;
    return;
  }

  container.innerHTML = state.planner
    .map(
      (task) => `
        <div class="planner-task ${
          task.completed ? "completed" : ""
        }">
          <button
            class="task-check"
            data-task="${task.id}"
          >
            ${task.completed ? "✓" : ""}
          </button>

          <div>
            <strong>${escapeHTML(task.title)}</strong>
            <small>${escapeHTML(task.subject || "Study")}</small>
          </div>

          <button
            class="task-delete"
            data-delete-task="${task.id}"
          >
            🗑️
          </button>
        </div>
      `
    )
    .join("");

  $$(".task-check", container).forEach((button) => {
    button.addEventListener("click", () => {
      const task = state.planner.find(
        (item) => item.id === Number(button.dataset.task)
      );

      if (!task) return;

      task.completed = !task.completed;

      saveData();
      renderPlanner();

      showToast(
        task.completed
          ? "Task completed 🎉"
          : "Task reopened",
        task.completed ? "✓" : "↩"
      );
    });
  });

  $$(".task-delete", container).forEach((button) => {
    button.addEventListener("click", () => {
      state.planner = state.planner.filter(
        (task) =>
          task.id !== Number(button.dataset.deleteTask)
      );

      saveData();
      renderPlanner();

      showToast("Task removed", "🗑️");
    });
  });
}

function addPlannerTask() {
  const title = prompt(
    "Enter your study task:"
  );

  if (!title?.trim()) return;

  state.planner.push({
    id: Date.now(),
    title: title.trim(),
    subject: "Study",
    completed: false,
    date: new Date().toISOString()
  });

  saveData();
  renderPlanner();

  addActivity(
    "Study task added",
    title.trim()
  );

  showToast("Task added to planner", "📅");
}

function setupPlanner() {
  $("#addTaskBtn")?.addEventListener(
    "click",
    addPlannerTask
  );
}

/* =========================
   STREAK
========================= */

function calculateStreak() {
  if (!state.activity.length) return 0;

  const dates = new Set();

  state.activity.forEach((item) => {
    const date = new Date(item.time);

    if (!Number.isNaN(date.getTime())) {
      dates.add(date.toDateString());
    }
  });

  let streak = 0;

  const current = new Date();

  while (dates.has(current.toDateString())) {
    streak++;

    current.setDate(
      current.getDate() - 1
    );
  }

  return streak;
}

/* =========================
   ANALYTICS
========================= */

function renderAnalytics() {
  const overall = calculateOverallProgress();

  $("#overallProgress") &&
    ($("#overallProgress").textContent = `${overall}%`);

  $("#analyticsQuestions") &&
    ($("#analyticsQuestions").textContent =
      questionBank.length);

  const totalAttempts =
    state.quizHistory.length +
    state.testHistory.length;

  $("#analyticsAccuracy") &&
    ($("#analyticsAccuracy").textContent =
      totalAttempts
        ? `${calculateAccuracy()}%`
        : "0%");

  $("#analyticsStreak") &&
    ($("#analyticsStreak").textContent =
      calculateStreak());

  const subjects = [
    ["Physics", state.progress.physics],
    ["Chemistry", state.progress.chemistry],
    ["Biology", state.progress.biology],
    ["Mathematics", state.progress.mathematics]
  ];

  const container = $("#page-analytics .subject-performance");

  if (container) {
    container.innerHTML = subjects
      .map(
        ([subject, value]) => `
          <div class="subject-row">
            <div class="subject-row-top">
              <span>${subject}</span>
              <strong>${Number(value) || 0}%</strong>
            </div>

            <div class="progress-line">
              <span
                style="width:${Number(value) || 0}%"
              ></span>
            </div>
          </div>
        `
      )
      .join("");
  }

  renderSimpleChart();
}

function calculateAccuracy() {
  let correct = 0;
  let total = 0;

  state.quizHistory.forEach((item) => {
    correct += Number(item.correct) || 0;
    total += Number(item.total) || 0;
  });

  state.testHistory.forEach((item) => {
    correct += Number(item.correct) || 0;
    total += Number(item.total) || 0;
  });

  if (!total) return 0;

  return Math.round((correct / total) * 100);
}

function renderSimpleChart() {
  const chart = $("#weeklyChart");

  if (!chart) return;

  const values = [20, 35, 30, 50, 45, 70, 60];

  chart.innerHTML = values
    .map(
      (value, index) => `
        <div class="chart-column">
          <div
            class="chart-bar"
            style="height:${value}%"
            title="${value}%"
          ></div>
          <small>
            ${["M", "T", "W", "T", "F", "S", "S"][index]}
          </small>
        </div>
      `
    )
    .join("");
}

/* =========================
   THEMES
========================= */

const themeNames = [
  "green",
  "dark",
  "light",
  "blue",
  "purple",
  "orange",
  "red",
  "rose",
  "ocean",
  "emerald",
  "midnight",
  "gold",
  "sunset",
  "cyber",
  "forest",
  "mint",
  "lime",
  "teal",
  "cyan",
  "indigo",
  "violet",
  "magenta",
  "pink",
  "crimson",
  "ruby",
  "coral",
  "amber",
  "yellow",
  "sky",
  "navy"
];

/*
  The first 30 themes are named.
  Additional generated themes make the Theme Library
  reach the planned 150-theme collection.
*/

const themeColors = {
  green: "#16a34a",
  dark: "#22c55e",
  light: "#16a34a",
  blue: "#3b82f6",
  purple: "#8b5cf6",
  orange: "#f97316",
  red: "#ef4444",
  rose: "#f43f5e",
  ocean: "#06b6d4",
  emerald: "#10b981",
  midnight: "#6366f1",
  gold: "#eab308",
  sunset: "#f97316",
  cyber: "#00e5ff",
  forest: "#15803d",
  mint: "#34d399",
  lime: "#84cc16",
  teal: "#14b8a6",
  cyan: "#06b6d4",
  indigo: "#6366f1",
  violet: "#7c3aed",
  magenta: "#d946ef",
  pink: "#ec4899",
  crimson: "#dc2626",
  ruby: "#e11d48",
  coral: "#fb7185",
  amber: "#f59e0b",
  yellow: "#eab308",
  sky: "#0ea5e9",
  navy: "#2563eb"
};

function getThemeColor(theme) {
  if (themeColors[theme]) {
    return themeColors[theme];
  }

  const hue = Number(
    theme.replace("theme-", "")
  ) || 120;

  return `hsl(${hue}, 70%, 48%)`;
}

function applyTheme(theme) {
  state.theme = theme;

  document.documentElement.dataset.theme = theme;

  const color = getThemeColor(theme);

  document.documentElement.style.setProperty(
    "--accent",
    color
  );

  document.documentElement.style.setProperty(
    "--accent-color",
    color
  );

  document.documentElement.style.setProperty(
    "--theme-accent",
    color
  );

  saveData();

  $$(".mode-btn").forEach((button) => {
    button.classList.toggle(
      "active",
      button.dataset.mode === theme
    );
  });

  renderThemeGrid();
}

function createThemeList() {
  const themes = [...themeNames];

  /*
    Generate themes until exactly 150 themes exist.
  */

  let number = 1;

  while (themes.length < 150) {
    const themeName = `theme-${number}`;

    if (!themes.includes(themeName)) {
      themes.push(themeName);
    }

    number++;
  }

  return themes;
}

function renderThemeGrid() {
  const grid = $("#themeGrid");

  if (!grid) return;

  const themes = createThemeList();

  grid.innerHTML = themes
    .map(
      (theme, index) => `
        <button
          class="theme-card ${
            state.theme === theme ? "active" : ""
          }"
          data-theme="${theme}"
        >
          <span
            class="theme-preview"
            style="--preview-color:${getThemeColor(theme)}"
          ></span>

          <strong>
            ${
              theme.startsWith("theme-")
                ? `Theme ${index + 1}`
                : capitalize(theme)
            }
          </strong>

          <small>
            ${state.theme === theme ? "Selected" : "Apply"}
          </small>
        </button>
      `
    )
    .join("");

  $$(".theme-card", grid).forEach((button) => {
    button.addEventListener("click", () => {
      applyTheme(button.dataset.theme);

      showToast(
        "Theme applied to the whole website",
        "🎨"
      );
    });
  });
}

function capitalize(text) {
  return String(text)
    .charAt(0)
    .toUpperCase() +
    String(text).slice(1);
}

function setupThemeButtons() {
  $$(".mode-btn").forEach((button) => {
    button.addEventListener("click", () => {
      applyTheme(
        button.dataset.mode || "green"
      );

      showToast(
        "Website theme changed",
        "🎨"
      );
    });
  });
}

/* =========================
   LANGUAGE
========================= */

const translations = {
  English: {
    dashboard: "Dashboard",
    courses: "Courses",
    quiz: "Quiz",
    tests: "Tests",
    notes: "Notes",
    planner: "Study Planner",
    bookmarks: "Bookmarks",
    analytics: "Analytics",
    ai: "AI Study Tutor"
  },

  Hindi: {
    dashboard: "डैशबोर्ड",
    courses: "कोर्स",
    quiz: "क्विज़",
    tests: "टेस्ट",
    notes: "नोट्स",
    planner: "स्टडी प्लानर",
    bookmarks: "बुकमार्क",
    analytics: "एनालिटिक्स",
    ai: "AI स्टडी ट्यूटर"
  }
};

function applyLanguage(language) {
  state.profile.language = language;

  saveData();

  const dictionary =
    translations[language] ||
    translations.English;

  $$("[data-i18n]").forEach((element) => {
    const key = element.dataset.i18n;

    if (dictionary[key]) {
      element.textContent = dictionary[key];
    }
  });

  $$(".language-btn").forEach((button) => {
    button.classList.toggle(
      "active",
      button.dataset.language === language
    );
  });

  showToast(
    language === "Hindi"
      ? "भाषा बदल दी गई"
      : "Language changed",
    "🌐"
  );
}

function setupLanguage() {
  $$(".language-btn").forEach((button) => {
    button.addEventListener("click", () => {
      applyLanguage(
        button.dataset.language || "English"
      );
    });
  });
}

/* =========================
   SEARCH
========================= */

function searchAll(query) {
  const text = query.trim().toLowerCase();

  if (!text) return [];

  const results = [];

  courses.forEach((course) => {
    if (
      `${course.title} ${course.subject} ${course.exam}`
        .toLowerCase()
        .includes(text)
    ) {
      results.push({
        type: "Course",
        title: course.title,
        page: "courses"
      });
    }
  });

  questionBank.forEach((question) => {
    if (
      `${question.question} ${question.subject} ${question.chapter}`
        .toLowerCase()
        .includes(text)
    ) {
      results.push({
        type: "Question",
        title: question.question,
        page: "question-bank"
      });
    }
  });

  notes.forEach((note) => {
    if (
      `${note.title} ${note.subject} ${note.text}`
        .toLowerCase()
        .includes(text)
    ) {
      results.push({
        type: "Note",
        title: note.title,
        page: "notes"
      });
    }
  });

  return results.slice(0, 8);
}

function setupSearch() {
  const input = $("#globalSearch");
  const suggestions = $("#searchSuggestions");

  if (!input) return;

  input.addEventListener("input", () => {
    const results = searchAll(input.value);

    if (!suggestions) return;

    if (!input.value.trim()) {
      suggestions.innerHTML = "";
      suggestions.classList.remove("show");
      return;
    }

    if (!results.length) {
      suggestions.innerHTML = `
        <div class="search-empty">
          No results found
        </div>
      `;

      suggestions.classList.add("show");

      return;
    }

    suggestions.innerHTML = results
      .map(
        (result) => `
          <button
            class="search-result"
            data-search-page="${result.page}"
          >
            <span>${result.type}</span>
            <strong>${escapeHTML(result.title)}</strong>
          </button>
        `
      )
      .join("");

    suggestions.classList.add("show");

    $$(".search-result", suggestions).forEach(
      (button) => {
        button.addEventListener("click", () => {
          showPage(button.dataset.searchPage);

          input.value = "";

          suggestions.classList.remove("show");
        });
      }
    );
  });

  document.addEventListener("click", (event) => {
    if (
      !event.target.closest(".global-search")
    ) {
      suggestions?.classList.remove("show");
    }
  });
}

/* =========================
   AI KHUSHI
========================= */

let aiHistory = [];

function addAIMessage(
  message,
  type = "ai"
) {
  const container = $("#aiMessages");

  if (!container) return;

  const avatar =
    type === "user"
      ? "👨‍🎓"
      : "👩‍🏫";

  const messageElement =
    document.createElement("div");

  messageElement.className =
    `ai-message ${type}`;

  messageElement.innerHTML = `
    <div class="message-avatar">
      ${avatar}
    </div>

    <div class="message-bubble">
      ${escapeHTML(message).replaceAll(
        "\n",
        "<br>"
      )}
    </div>
  `;

  container.appendChild(messageElement);

  container.scrollTop =
    container.scrollHeight;
}

async function askKhushi(question) {
  const text = question.trim();

  if (!text) return;

  addAIMessage(text, "user");

  aiHistory.push({
    role: "user",
    text
  });

  const thinkingId =
    `thinking-${Date.now()}`;

  const container = $("#aiMessages");

  if (container) {
    const thinking =
      document.createElement("div");

    thinking.id = thinkingId;
    thinking.className =
      "ai-message ai";

    thinking.innerHTML = `
      <div class="message-avatar">👩‍🏫</div>
      <div class="message-bubble">
        Khushi is thinking...
      </div>
    `;

    container.appendChild(thinking);

    container.scrollTop =
      container.scrollHeight;
  }

  try {
    const response = await fetch(
      "/api/chat",
      {
        method: "POST",
        headers: {
          "Content-Type":
            "application/json"
        },

        body: JSON.stringify({
          message: text,
          history: aiHistory.slice(-12)
        })
      }
    );

    const data =
      await response.json();

    $(`#${thinkingId}`)?.remove();

    if (!response.ok || !data.success) {
      throw new Error(
        data.error ||
          "AI request failed."
      );
    }

    addAIMessage(
      data.answer,
      "ai"
    );

    aiHistory.push({
      role: "model",
      text: data.answer
    });

    addActivity(
      "Asked Khushi AI",
      "AI Study Tutor"
    );
  } catch (error) {
    $(`#${thinkingId}`)?.remove();

    addAIMessage(
      "Sorry, I could not connect to Khushi right now. Please check your Vercel API setup and try again.",
      "ai"
    );

    showToast(
      "AI connection problem",
      "⚠️"
    );
  }
}

function setupAI() {
  $("#aiForm")?.addEventListener(
    "submit",
    (event) => {
      event.preventDefault();

      const input = $("#aiInput");

      if (!input) return;

      const question =
        input.value.trim();

      if (!question) return;

      input.value = "";

      askKhushi(question);
    }
  );

  $$(".ai-quick-prompts").forEach(
    (container) => {
      container.addEventListener(
        "click",
        (event) => {
          const button =
            event.target.closest(
              "button"
            );

          if (!button) return;

          const prompt =
            button.dataset.prompt ||
            button.textContent;

          const input = $("#aiInput");

          if (input) {
            input.value = prompt;
            input.focus();
          }
        }
      );
    }
  );

  $$(".language-switch").forEach(
    (container) => {
      container.addEventListener(
        "click",
        (event) => {
          const button =
            event.target.closest(
              ".language-btn"
            );

          if (!button) return;

          applyLanguage(
            button.dataset.language ||
              "English"
          );
        }
      );
    }
  );
}

/* =========================
   AI IMAGE UPLOAD
========================= */

function setupAIImageUpload() {
  const button = $("#aiImageBtn");
  const input = $("#aiImageInput");

  if (!button || !input) return;

  button.addEventListener("click", () => {
    input.click();
  });

  input.addEventListener(
    "change",
    () => {
      const file =
        input.files?.[0];

      if (!file) return;

      showToast(
        "Image selected. Image AI analysis can be connected to the backend.",
        "🖼️"
      );
    }
  );
}

/* =========================
   VOICE INPUT
========================= */

function setupVoiceInput() {
  const button = $("#aiVoiceBtn");
  const input = $("#aiInput");

  if (!button || !input) return;

  const SpeechRecognition =
    window.SpeechRecognition ||
    window.webkitSpeechRecognition;

  if (!SpeechRecognition) {
    button.addEventListener(
      "click",
      () => {
        showToast(
          "Voice input is not supported by this browser.",
          "⚠️"
        );
      }
    );

    return;
  }

  const recognition =
    new SpeechRecognition();

  recognition.lang = "hi-IN";
  recognition.continuous = false;
  recognition.interimResults = false;

  button.addEventListener(
    "click",
    () => {
      recognition.start();

      showToast(
        "Listening...",
        "🎙️"
      );
    }
  );

  recognition.onresult = (event) => {
    const transcript =
      event.results[0][0].transcript;

    input.value = transcript;
    input.focus();
  };

  recognition.onerror = () => {
    showToast(
      "Voice input could not start.",
      "⚠️"
    );
  };
}

/* =========================
   YOUTUBE LEARNING
========================= */

function setupYouTubePage() {
  const searchButton =
    $("#youtubeSearchBtn");

  if (!searchButton) return;

  if (searchButton.dataset.ready) {
    return;
  }

  searchButton.dataset.ready = "true";

  searchButton.addEventListener(
    "click",
    searchYouTube
  );
}

async function searchYouTube() {
  const input =
    $("#youtubeSearch");

  const subject =
    $("#youtubeSubject");

  const results =
    $("#youtubeResults");

  if (!results) return;

  const query =
    input?.value.trim() || "NEET JEE";

  results.innerHTML = `
    <div class="empty-state">
      <div class="empty-icon">⏳</div>
      <h3>Searching YouTube...</h3>
      <p>Please wait.</p>
    </div>
  `;

  try {
    const params =
      new URLSearchParams();

    params.set("q", query);

    if (
      subject?.value &&
      subject.value !== "ALL"
    ) {
      params.set(
        "subject",
        subject.value
      );
    }

    const response =
      await fetch(
        `/api/youtube?${params.toString()}`
      );

    const data =
      await response.json();

    if (!response.ok) {
      throw new Error(
        data.error ||
          "YouTube search failed."
      );
    }

    const videos =
      data.videos || [];

    if (!videos.length) {
      results.innerHTML = `
        <div class="empty-state">
          <div class="empty-icon">▶️</div>
          <h3>No videos found</h3>
          <p>Try another search.</p>
        </div>
      `;

      return;
    }

    results.innerHTML = `
      <div class="video-grid">
        ${videos
          .map(
            (video) => `
              <article class="video-card">
                <button
                  class="video-open"
                  data-video-id="${escapeHTML(
                    video.videoId
                  )}"
                >
                  <div class="video-thumb">
                    <img
                      src="${escapeHTML(
                        video.thumbnail
                      )}"
                      alt=""
                      loading="lazy"
                    />

                    <span class="play-button">
                      ▶
                    </span>
                  </div>

                  <div class="video-body">
                    <span>
                      ${escapeHTML(
                        video.channelTitle ||
                          "YouTube"
                      )}
                    </span>

                    <h3>
                      ${escapeHTML(
                        video.title
                      )}
                    </h3>
                  </div>
                </button>
              </article>
            `
          )
          .join("")}
      </div>
    `;

    $$(".video-open", results).forEach(
      (button) => {
        button.addEventListener(
          "click",
          () => {
            openYouTubeVideo(
              button.dataset.videoId
            );
          }
        );
      }
    );
  } catch (error) {
    results.innerHTML = `
      <div class="empty-state">
        <div class="empty-icon">⚠️</div>
        <h3>YouTube unavailable</h3>
        <p>
          Check the YouTube API environment variable
          on Vercel.
        </p>
      </div>
    `;
  }
}

function openYouTubeVideo(videoId) {
  if (!videoId) return;

  openModal(`
    <div class="youtube-player-modal">
      <iframe
        src="https://www.youtube.com/embed/${encodeURIComponent(
          videoId
        )}"
        title="YouTube video player"
        allowfullscreen
        loading="lazy"
      ></iframe>
    </div>
  `);
}

/* =========================
   NOTIFICATIONS
========================= */

function renderNotifications() {
  const container =
    $("#notificationPanel");

  if (!container) return;

  if (!state.notifications.length) {
    container.innerHTML = `
      <div class="empty-state">
        <div class="empty-icon">🔔</div>
        <h3>No notifications</h3>
      </div>
    `;

    return;
  }

  container.innerHTML =
    state.notifications
      .map(
        (notification) => `
          <div class="notification-item ${
            notification.read
              ? "read"
              : ""
          }">
            <div class="notification-icon">
              🔔
            </div>

            <div>
              <strong>
                ${escapeHTML(
                  notification.title
                )}
              </strong>

              <p>
                ${escapeHTML(
                  notification.text
                )}
              </p>

              <small>
                ${escapeHTML(
                  notification.time
                )}
              </small>
            </div>
          </div>
        `
      )
      .join("");
}

function setupNotifications() {
  const button =
    $(".notification-btn");

  if (!button) return;

  button.addEventListener(
    "click",
    () => {
      openModal(`
        <div class="modal-icon">🔔</div>

        <h2>Notifications</h2>

        <div id="notificationPanel"></div>

        <button
          class="secondary-btn"
          id="markNotificationsRead"
        >
          Mark all as read
        </button>
      `);

      renderNotifications();

      $("#markNotificationsRead")
        ?.addEventListener(
          "click",
          () => {
            state.notifications.forEach(
              (item) => {
                item.read = true;
              }
            );

            saveData();

            closeModal();

            showToast(
              "Notifications marked as read",
              "✓"
            );
          }
        );
    }
  );
}

/* =========================
   PROFILE SAVE
========================= */

function setupProfile() {
  $("#saveProfileBtn")?.addEventListener(
    "click",
    () => {
      const name =
        $("#profileNameInput")?.value.trim();

      const exam =
        $("#profileExam")?.value ||
        "NEET";

      if (!name) {
        showToast(
          "Please enter your name.",
          "⚠️"
        );

        return;
      }

      state.profile.name = name;
      state.profile.exam = exam;

      saveData();
      updateProfileUI();

      addActivity(
        "Profile updated",
        `${name} • ${exam}`
      );

      showToast(
        "Profile saved successfully",
        "✓"
      );
    }
  );
}

/* =========================
   SETTINGS
========================= */

function setupSettings() {
  $("#studyReminderToggle")?.addEventListener(
    "change",
    (event) => {
      state.settings.studyReminder =
        event.target.checked;

      saveData();

      showToast(
        "Study reminder updated",
        "🔔"
      );
    }
  );

  $("#testReminderToggle")?.addEventListener(
    "change",
    (event) => {
      state.settings.testReminder =
        event.target.checked;

      saveData();

      showToast(
        "Test reminder updated",
        "🔔"
      );
    }
  );

  $("#resetDataBtn")?.addEventListener(
    "click",
    () => {
      const confirmed =
        confirm(
          "Reset Study Wallah saved data?"
        );

      if (!confirmed) return;

      localStorage.removeItem(
        STORAGE_KEY
      );

      location.reload();
    }
  );
}

/* =========================
   GENERIC BUTTONS
========================= */

function setupGenericButtons() {
  document.addEventListener(
    "click",
    (event) => {
      const button =
        event.target.closest(
          "[data-action]"
        );

      if (!button) return;

      const action =
        button.dataset.action;

      if (action === "study") {
        showToast(
          "Study session started",
          "📚"
        );

        addActivity(
          "Study session started",
          "Keep going!"
        );
      }

      if (action === "ai") {
        showPage("ai");
      }

      if (action === "quiz") {
        showPage("quiz");
      }

      if (action === "test") {
        showPage("tests");
      }

      if (action === "courses") {
        showPage("courses");
      }
    }
  );
}

/* =========================
   STUDY TIMER
========================= */

let studyTimerSeconds = 0;
let studyTimerInterval = null;

function startStudyTimer(minutes = 25) {
  studyTimerSeconds = minutes * 60;

  clearInterval(
    studyTimerInterval
  );

  studyTimerInterval =
    setInterval(() => {
      studyTimerSeconds--;

      if (
        studyTimerSeconds <= 0
      ) {
        clearInterval(
          studyTimerInterval
        );

        showToast(
          "Study session complete! 🎉",
          "⏱️"
        );

        addActivity(
          "Study session completed",
          `${minutes} minute session`
        );

        return;
      }
    }, 1000);

  showToast(
    `${minutes} minute study session started`,
    "⏱️"
  );
}

/* =========================
   FORMAT TIME
========================= */

function formatTime(seconds) {
  seconds = Math.max(
    Number(seconds) || 0,
    0
  );

  const minutes =
    Math.floor(seconds / 60);

  const remaining =
    seconds % 60;

  return `${String(minutes).padStart(
    2,
    "0"
  )}:${String(remaining).padStart(
    2,
    "0"
  )}`;
}

/* =========================
   CURRENT YEAR
========================= */

function updateYear() {
  const year = $("#currentYear");

  if (year) {
    year.textContent =
      new Date().getFullYear();
  }
}

/* =========================
   ONLINE / OFFLINE
========================= */

function setupConnectionStatus() {
  window.addEventListener(
    "online",
    () => {
      showToast(
        "You are back online",
        "🌐"
      );
    }
  );

  window.addEventListener(
    "offline",
    () => {
      showToast(
        "You are offline",
        "⚠️"
      );
    }
  );
}

/* =========================
   FILTERS
========================= */

function setupFilters() {
  [
    "#dppExamFilter",
    "#dppSubjectFilter",
    "#dppDifficulty"
  ].forEach((selector) => {
    $(selector)?.addEventListener(
      "change",
      renderDPP
    );
  });
}

/* =========================
   HOME QUICK ACTIONS
========================= */

function setupHomeActions() {
  $$(
    ".quick-card, .hero-actions button"
  ).forEach((button) => {
    button.addEventListener(
      "click",
      () => {
        const page =
          button.dataset.page;

        if (page) {
          showPage(page);
        }
      }
    );
  });
}

/* =========================
   SIDEBAR CLOSE ON MOBILE
========================= */

function setupResponsiveNavigation() {
  $$(".nav-item").forEach((item) => {
    item.addEventListener(
      "click",
      () => {
        if (
          window.innerWidth <= 900
        ) {
          closeMobileMenu();
        }
      }
    );
  });
}

/* =========================
   INITIAL DASHBOARD
========================= */

function initializeDashboard() {
  updateProfileUI();
  updateProgressUI();
  renderRecentActivity();
  updateBookmarkCount();

  const overall =
    calculateOverallProgress();

  const dashboardProgress =
    $("#dashboardProgress");

  if (dashboardProgress) {
    dashboardProgress.style.width =
      `${overall}%`;
  }
}

/* =========================
   INITIALIZE APP
========================= */

function initializeApp() {
  applyTheme(
    state.theme || "green"
  );

  applyLanguage(
    state.profile.language ||
      "English"
  );

  setupNavigation();
  setupMobileMenu();
  setupModal();

  setupQuiz();
  setupTests();

  setupPlanner();

  setupAI();
  setupAIImageUpload();
  setupVoiceInput();

  setupYouTubePage();

  setupProfile();
  setupSettings();

  setupThemeButtons();
  setupLanguage();

  setupSearch();
  setupFilters();

  setupNotifications();
  setupGenericButtons();

  setupConnectionStatus();
  setupResponsiveNavigation();

  setupHomeActions();

  renderCourses();
  renderDPP();
  renderQuestionBank();
  renderNotes();
  renderPlanner();
  renderBookmarks();
  renderAnalytics();
  renderThemeGrid();

  initializeDashboard();
  updateYear();

  hideLoader();

  console.log(
    "Study Wallah loaded successfully."
  );
}

/* =========================
   DOM READY
========================= */

if (
  document.readyState ===
  "loading"
) {
  document.addEventListener(
    "DOMContentLoaded",
    initializeApp
  );
} else {
  initializeApp();
}

/* =========================
   PUBLIC API
========================= */

window.StudyWallah = {
  state,

  showPage,

  applyTheme,

  applyLanguage,

  askKhushi,

  startQuiz,

  startTest,

  startStudyTimer,

  addActivity,

  toggleBookmark,

  renderCourses,

  renderDPP,

  renderQuestionBank,

  renderAnalytics,

  renderPlanner,

  renderBookmarks,

  searchYouTube
};
