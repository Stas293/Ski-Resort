function changeResponsive() {
  const x = document.getElementById("menu");
  if (x.className === "responsive") {
    x.classList.remove("responsive");
  }
  else {
    x.classList.add("responsive");
  }
}