// Dark Mode Toggle Functionality
(function () {
  "use strict";

  const THEME_KEY = "tunehub-theme";
  const DARK_CLASS = "dark";

  // Get theme from localStorage or system preference
  function getInitialTheme() {
    const savedTheme = localStorage.getItem(THEME_KEY);

    if (savedTheme) {
      return savedTheme;
    }

    // Check system preference
    if (
      window.matchMedia &&
      window.matchMedia("(prefers-color-scheme: dark)").matches
    ) {
      return "dark";
    }

    return "light";
  }

  // Apply theme to document
  function applyTheme(theme) {
    const html = document.documentElement;

    if (theme === "dark") {
      html.classList.add(DARK_CLASS);
    } else {
      html.classList.remove(DARK_CLASS);
    }

    // Save to localStorage
    localStorage.setItem(THEME_KEY, theme);

    // Update toggle button icon if it exists
    updateToggleIcon(theme);
  }

  // Update toggle button icon
  function updateToggleIcon(theme) {
    const sunIcon = document.getElementById("theme-toggle-sun-icon");
    const moonIcon = document.getElementById("theme-toggle-moon-icon");

    if (sunIcon && moonIcon) {
      if (theme === "dark") {
        sunIcon.classList.remove("hidden");
        moonIcon.classList.add("hidden");
      } else {
        sunIcon.classList.add("hidden");
        moonIcon.classList.remove("hidden");
      }
    }
  }

  // Toggle theme
  function toggleTheme() {
    const html = document.documentElement;
    const currentTheme = html.classList.contains(DARK_CLASS) ? "dark" : "light";
    const newTheme = currentTheme === "dark" ? "light" : "dark";

    applyTheme(newTheme);
  }

  // Initialize theme on page load
  function init() {
    const initialTheme = getInitialTheme();
    applyTheme(initialTheme);

    // Set up toggle button click handler
    const toggleButton = document.getElementById("theme-toggle");
    if (toggleButton) {
      toggleButton.addEventListener("click", toggleTheme);
    }
  }

  // Apply theme immediately (before page render)
  const initialTheme = getInitialTheme();
  if (initialTheme === "dark") {
    document.documentElement.classList.add(DARK_CLASS);
  }

  // Initialize when DOM is ready
  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init);
  } else {
    init();
  }

  // Listen for system theme changes
  if (window.matchMedia) {
    window
      .matchMedia("(prefers-color-scheme: dark)")
      .addEventListener("change", (e) => {
        // Only apply system preference if user hasn't manually set a theme
        if (!localStorage.getItem(THEME_KEY)) {
          applyTheme(e.matches ? "dark" : "light");
        }
      });
  }

  // Expose toggle function globally for inline event handlers
  window.toggleDarkMode = toggleTheme;
})();
