-- Función que sincroniza la identidad mínima registrada en auth.users
CREATE OR REPLACE FUNCTION public.handle_new_user()
RETURNS TRIGGER AS $$
BEGIN
INSERT INTO public.usuario (
    usu_id,
    usu_email,
    roles_rol_id
)
VALUES (
           NEW.id,
           NEW.email,
           COALESCE((NEW.raw_user_meta_data->>'rol_id')::bigint, 4) -- 4 = Estudiante por defecto
       );
RETURN NEW;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

-- Trigger disparado tras cada inserción en la tabla de autenticación
DROP TRIGGER IF EXISTS on_auth_user_created ON auth.users;
CREATE TRIGGER on_auth_user_created
    AFTER INSERT ON auth.users
    FOR EACH ROW EXECUTE FUNCTION public.handle_new_user();