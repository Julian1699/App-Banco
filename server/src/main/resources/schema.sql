-- Función de auditoría para manejar creación y actualización por separado
CREATE OR REPLACE FUNCTION funcion_auditoria_tabla()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        -- En una inserción, establecer solo los valores de creación
        NEW.created_by := current_user;
        NEW.created_at := CURRENT_TIMESTAMP;
    ELSIF (TG_OP = 'UPDATE') THEN
        -- En una actualización, solo establecer valores de modificación
        NEW.updated_by := current_user;
        NEW.updated_at := CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- TRIGGERS PARA LA TABLA `listas`
CREATE TRIGGER trg_insert_auditoria_listas
BEFORE INSERT ON listas
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

CREATE TRIGGER trg_update_auditoria_listas
BEFORE UPDATE ON listas
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

-- TRIGGERS PARA LA TABLA `valores_listas`
CREATE TRIGGER trg_insert_auditoria_valores_listas
BEFORE INSERT ON valores_listas
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

CREATE TRIGGER trg_update_auditoria_valores_listas
BEFORE UPDATE ON valores_listas
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

-- TRIGGERS PARA LA TABLA `paises`
CREATE TRIGGER trg_insert_auditoria_paises
BEFORE INSERT ON paises
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

CREATE TRIGGER trg_update_auditoria_paises
BEFORE UPDATE ON paises
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

-- TRIGGERS PARA LA TABLA `departamentos`
CREATE TRIGGER trg_insert_auditoria_departamentos
BEFORE INSERT ON departamentos
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

CREATE TRIGGER trg_update_auditoria_departamentos
BEFORE UPDATE ON departamentos
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

-- TRIGGERS PARA LA TABLA `ciudades`
CREATE TRIGGER trg_insert_auditoria_ciudades
BEFORE INSERT ON ciudades
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

CREATE TRIGGER trg_update_auditoria_ciudades
BEFORE UPDATE ON ciudades
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

-- TRIGGERS PARA LA TABLA `permisos`
CREATE TRIGGER trg_insert_auditoria_permisos
BEFORE INSERT ON permisos
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

CREATE TRIGGER trg_update_auditoria_permisos
BEFORE UPDATE ON permisos
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

-- TRIGGERS PARA LA TABLA `sedes`
CREATE TRIGGER trg_insert_auditoria_sedes
BEFORE INSERT ON sedes
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();

CREATE TRIGGER trg_update_auditoria_sedes
BEFORE UPDATE ON sedes
FOR EACH ROW
EXECUTE FUNCTION funcion_auditoria_tabla();
