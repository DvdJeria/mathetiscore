export interface UserResponseDto {
  id: string;
  rut: string;
  nombre: string;
  apellidoPaterno: string;
  apellidoMaterno?: string;
  email?: string;
  rolNombre?: string;
}
