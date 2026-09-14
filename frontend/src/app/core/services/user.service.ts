import {Injectable, signal, computed, inject} from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError, tap } from "rxjs/operators";
import { environment } from "../../../environments/environment";
import { UserResponseDto} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/api/v1/users`;

  //Signal privado para el estado interno
  private readonly profileSignal = signal<UserResponseDto | null>(null);
  //Signal público de solo lectura
  readonly userProfile = this.profileSignal.asReadonly()

  //Signal computado para verificar rápidamente si el perfil ya fue cargado
  readonly hasProfile = computed(() => this.userProfile() !== null);

  /*
  * Obtiene el perfil de usuario desde Spring Boot usando su UUID (backend)
  * @param userId UUID del usuario autenticado
  */
  fetchUserProfile(userId : string): Observable<UserResponseDto> {
    return this.http.get<UserResponseDto>(this.apiUrl + '/' + userId).pipe(
      tap((profile) => this.profileSignal.set(profile)),
      catchError((error: HttpErrorResponse) => {
        if(error.status === 404){
          console.error(`Usuario con ID ${userId} no encontrado en la base de datos local.`)
        }
        this.profileSignal.set(null);
        return throwError(() => error);
      })
    );
  }

  clearProfile(): void {
    this.profileSignal.set(null)
  }
}
