import { Component } from '@angular/core';
import { CustomComponent, Illustrations } from '../Model/Coordinates';
import { IllustrationsService } from '../services/illustrations.service';

@Component({
selector: 'app-illustrations',
templateUrl: './illustrations.component.html',
styleUrls: ['./illustrations.component.css']
})
export class IllustrationsComponent {
coordinates: Illustrations = new Illustrations();
imageFile: File | null = null;
imagePreview: string | ArrayBuffer | null = null;
cursorPosition: { x: number, y: number } | null = null;

constructor(private illustrationsService: IllustrationsService) {}

onFileChange(event: Event): void {
const target = event.target as HTMLInputElement;
const files = target.files;

if (files && files.length > 0) {
this.imageFile = files[0];
this.previewImage();
}
}

previewImage(): void {
if (this.imageFile) {
const reader = new FileReader();
reader.onload = () => {
this.imagePreview = reader.result;
};
reader.readAsDataURL(this.imageFile);
}
}

onImageClick(event: MouseEvent): void {
const target = event.target as HTMLImageElement;
const boundingBox = target.getBoundingClientRect();
const x = event.clientX - boundingBox.left;
const y = event.clientY - boundingBox.top;


this.cursorPosition = { x, y };


this.coordinates.x = x;
this.coordinates.y = y;
}

addCoordinates(): void {

const component: CustomComponent = {
id: undefined,
componentName: 'Component',
componentX: this.coordinates.x,
componentY: this.coordinates.y
};
if (!this.coordinates.components) {
this.coordinates.components = [];
}
this.coordinates.components.push(component);
const formData = new FormData();
formData.append('illustration', JSON.stringify(this.coordinates));
formData.append('x', this.coordinates.x.toString());
formData.append('y', this.coordinates.y.toString());
if (this.imageFile) {
formData.append('image', this.imageFile, this.imageFile.name);
}
const addCoordinatesObservable = this.illustrationsService.addCoordinatesWithImage(formData);
addCoordinatesObservable.subscribe(
(newCoordinates) => {
console.log('Coordinates added:', newCoordinates);

},
(error) => {
console.error('Error adding coordinates:', error);

}
);
}
}