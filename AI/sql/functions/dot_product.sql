DELIMITER //

CREATE FUNCTION my_dot_product(v1 VECTOR, v2 VECTOR) 
RETURNS FLOAT DETERMINISTIC
BEGIN
    DECLARE total FLOAT DEFAULT 0;
    DECLARE i INT DEFAULT 0;
    DECLARE dim INT;
    -- Convert Vector to String, then to JSON
    DECLARE j1 JSON DEFAULT CAST(VECTOR_TO_STRING(v1) AS JSON);
    DECLARE j2 JSON DEFAULT CAST(VECTOR_TO_STRING(v2) AS JSON);
    
    SET dim = JSON_LENGTH(j1);
    
    WHILE i < dim DO
        SET total = total + (JSON_EXTRACT(j1, CONCAT('$[', i, ']')) * JSON_EXTRACT(j2, CONCAT('$[', i, ']')));
        SET i = i + 1;
    END WHILE;
    
    RETURN total;
END //

DELIMITER ;
