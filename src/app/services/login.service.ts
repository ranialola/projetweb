import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private baseUrl = 'http://localhost:8081'; 

  constructor(private http: HttpClient) {}

  login(credentials: { mail: string, mdp: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/LogIn`, credentials);
  }

  

  isAdmin(token: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/isAdmin`, {
      headers: { Authorization: `Bearer ${token}` }
    });
  }
  getToken(): string | null {
    // Logique pour obtenir le jeton, par exemple à partir du stockage local
    return localStorage.getItem('access_token');
  }
  getAllMembers(token: string): Observable<any[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  
    const options = { headers, withCredentials: true };
  
    return this.http.get<any[]>(`${this.baseUrl}/api/members`,{
      headers: { Authorization: `Bearer ${token}` }
    });
  }
  getMemberById(id: number): Observable<any> {
    const token = this.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  
    return this.http.get<any>(`${this.baseUrl}/api/getUserById/${id}`, { headers });
  }
  updateMember(id: number, updatedData: any): Observable<any> {
    const token = this.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  
    
    headers.append('Content-Type', 'text/plain');
  
    return this.http.put<any>(`${this.baseUrl}/api/updateUser/${id}`, updatedData, { headers, responseType: 'text' as 'json' });
  }
  addNewMember(newMemberData: any): Observable<any> {
    const token = this.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    headers.append('Content-Type', 'text/plain');

    return this.http.post<any>(`${this.baseUrl}/api/admin/addUser`, newMemberData, { headers, responseType: 'text' as 'json' });
  }
  
  loginForMember(credentials: { mail: string, mdp: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/LogIn`, credentials);
  }
  
}
