import { FileCard } from "./file-card.js";

export class FileGrid {
  private element: HTMLElement;
  private fileCardTemplate: HTMLTemplateElement;
  private fileCards: FileCard[] = [];

  public constructor() {
    this.element = document.getElementById("file-grid")!;
    this.fileCardTemplate = document.getElementById(
      "file-card"
    )! as HTMLTemplateElement;

    // const fileCard = new FileCard(this.fileCardTemplate);

    this.getUserFileNames().then((fileNames) => {
      for (const fileName of fileNames) {
        const fileCard = new FileCard(this.fileCardTemplate, fileName);
        this.fileCards.push(fileCard);
      }
      this.render();
    });
  }

  private async getUserFileNames(): Promise<string[]> {
    return fetch("/api/files")
      .then((res) => res.json())
      .then((res) => {
        return res as string[];
      });
  }

  private render() {
    for (const fileCard of this.fileCards) {
      this.element.appendChild(fileCard.element);
    }
  }
}
