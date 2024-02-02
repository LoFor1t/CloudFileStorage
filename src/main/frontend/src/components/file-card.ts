export class FileCard {
    private _element: HTMLElement;
    private changeNameForm: HTMLFormElement;
    private newFileNameInput: HTMLInputElement;

    constructor(template: HTMLTemplateElement, private fileName: string) {
        const importedNode = document.importNode(template.content, true);
        this._element = importedNode.firstElementChild as HTMLElement;
        this.changeNameForm = this._element.getElementsByClassName(
            "change-name-form"
        )![0] as HTMLFormElement;

        this.newFileNameInput = this.changeNameForm.elements.namedItem(
            "newFileName"
        ) as HTMLInputElement;

        this.configure();
    }

    get element(): HTMLElement {
        return this._element;
    }

    private configure() {
        this.changeVisibilityOfChangeNameForm();

        this.element.getElementsByClassName("file-name")![0].textContent =
            this.fileName;

        this.element
            .getElementsByClassName("delete-btn")![0]
            .addEventListener("click", this.deleteFile.bind(this));

        this.element
            .getElementsByClassName("change-name-btn")![0]
            .addEventListener("click", this.showChangeNameForm.bind(this));

        this.changeNameForm.addEventListener("submit", this.changeName.bind(this));
    }

    private async deleteFile() {
        const queryString = new URLSearchParams({
            fileName: this.fileName,
        }).toString();

        console.log(queryString);

        const res = await fetch(`/api/files/delete?${queryString}`, {
            method: "GET",
        });
        if (res.status === 200) {
            this.element.remove();
        }
    }

    private showChangeNameForm() {
        this.changeVisibilityOfChangeNameForm();
        this.newFileNameInput.value = this.fileName.split(".")[0];
    }

    private async changeName(event: Event) {
        event.preventDefault();
        const newFileName = this.newFileNameInput.value;
        const queryString = new URLSearchParams({
            oldFileName: this.fileName,
            newFileName: newFileName,
        }).toString();

        const res = await fetch(`/api/files/rename?${queryString}`, {
            method: "GET",
        });
        if (res.status === 200) {
            this.fileName = newFileName;
            this.element.getElementsByClassName("file-name")![0].textContent =
                newFileName;
            this.changeVisibilityOfChangeNameForm();
        }
    }

    private changeVisibilityOfChangeNameForm() {
        this.changeNameForm.style.display =
            this.changeNameForm.style.display === "none" ? "" : "none";
    }
}
