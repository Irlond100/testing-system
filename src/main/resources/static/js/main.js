// Плавная прокрутка
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener("click", function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute("href")).scrollIntoView({
            behavior: "smooth"
        });
    });
});

// Анимация карточек при скролле
const cards = document.querySelectorAll(".feature-card");
const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add("show");
            observer.unobserve(entry.target);
        }
    });
}, { threshold: 0.2 });
cards.forEach(card => observer.observe(card));

// Пример валидации AJAX формы
$(document).on("submit", "form.ajax-form", function (e) {
    e.preventDefault();
    const $form = $(this);

    let valid = true;
    $form.find("input[required]").each(function () {
        if (!$(this).val().trim()) {
            $(this).css("border", "2px solid red");
            valid = false;
        } else {
            $(this).css("border", "");
        }
    });
    if (!valid) return;

    $.ajax({
        url: $form.attr("action"),
        type: $form.attr("method") || "POST",
        data: $form.serialize(),
        success: function () {
            alert("Успех ✅");
        },
        error: function () {
            alert("Ошибка ❌");
        }
    });
});
