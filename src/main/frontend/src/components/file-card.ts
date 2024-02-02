export class FileCard {
  private _element: HTMLElement;

  constructor(template: HTMLTemplateElement, private fileName: string) {
    const importedNode = document.importNode(template.content, true);
    this._element = importedNode.firstElementChild as HTMLElement;

    this.configure();
  }

  get element(): HTMLElement {
    return this._element;
  }

  private configure() {
    const changeNameForm = this._element.querySelector(
      "#change-name-form"
    )! as HTMLDivElement;
    changeNameForm.style.display = "none";

    this.element.getElementsByClassName("file-name")![0].textContent =
      this.fileName;

    this.element
      .getElementsByClassName("delete-btn")![0]
      .addEventListener("click", this.deleteFile.bind(this));
  }

  private deleteFile() {
    const queryString = new URLSearchParams({
      fileName: this.fileName,
    }).toString();

    console.log(queryString);

    return fetch(`/api/files/delete?${queryString}`, { method: "GET" }).then(
      (res) => {
        if (res.status === 200) {
          this.element.remove();
        }
      }
    );
  }
}
