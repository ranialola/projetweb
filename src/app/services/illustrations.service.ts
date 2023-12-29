import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Illustrations } from '../Model/Coordinates';

@Injectable({
providedIn: 'root'
})
export class IllustrationsService {
private apiUrl = 'http://localhost:8081/api/illustrations';

constructor(private http: HttpClient) {}

getAllCoordinates(): Observable<Illustrations[]> {
return this.http.get<Illustrations[]>(this.apiUrl);
}

addCoordinates(coordinates: Illustrations): Observable<Illustrations> {
return this.http.post<Illustrations>(this.apiUrl, coordinates);
}

addCoordinatesWithImage(formData: FormData): Observable<Illustrations> {
return this.http.post<Illustrations>(`${this.apiUrl}/addWithImage`, formData).pipe(
catchError(error => {
console.error('Error adding coordinates with image:', error);
return throwError(error);
})
);
}

deleteCoordinates(id: number): Observable<void> {
return this.http.delete<void>(`${this.apiUrl}/${id}`);
}
}