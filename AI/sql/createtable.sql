    /*This SQL is to create the table with Vector , MySQL Version 9.4
     *The size of the Vector is set to (512) , 
     * The INSERT statement has 512 floating point number 
     * and this is the correct match for Common Model Source - CLIP / Truncated OpenAI */
    CREATE TABLE image_embeddings (
        id INT PRIMARY KEY AUTO_INCREMENT,
        image_name VARCHAR(255),
        embedding VECTOR(512) -- Example for a 512-dimensional float vector
    );
