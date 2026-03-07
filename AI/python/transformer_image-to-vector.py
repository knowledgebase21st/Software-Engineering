import glob
import sys
import os

# Test it!
if len(sys.argv) > 2:
    imageDir = sys.argv[1]
    sqlFilePath = sys.argv[2]
    if os.path.exists(sqlFilePath):
        os.remove(sqlFilePath)
else:
    print("Usage: python3 transformer_image-to-vector.py <image directory> <path of the output sql")
    sys.exit();

from sentence_transformers import SentenceTransformer
from PIL import Image
import json


# 1. Load the CLIP model (this is the modern equivalent of imgbeddings)
# It downloads about 600MB on the first run.
model = SentenceTransformer('clip-ViT-B-32')

#This is the Function to generate the sql from the image and write to a desired file
def get_sql_insert(image_path,file_path_to_write):
    try:
        print("Will write the image {} to file: {}".format(image_path,file_path_to_write))
        # 2. Load and process the image
        img = Image.open(image_path)
        
        # 3. Generate the vector (embedding)
        # We use .tolist() to turn the math array into a Python list
        embedding = model.encode(img).tolist()
        
        # 4. Format the list as a string for SQL [0.1, 0.2, ...]
        vector_string = json.dumps(embedding)
        
        # 5. Create the SQL command
        sql = "--This sql is genrated from the image using  the model SentenceTransformer('clip-ViT-B-32')\n"
        sql = sql + f"INSERT INTO image_embeddings (image_name, embedding) VALUES ('{image_path}', STRING_TO_VECTOR('{vector_string}'));\n"
        with open(file_path_to_write, "a") as file:
            file.write(sql)
        return ("Successfully Wrote the image {} to file: {}".format(image_path,file_path_to_write))

    except Exception as e:
        return f"Error: {e}"

#It will take all the .jpg files from the image directory (First parameter of this python script) 
#and write to the desired file (2nd parameter of this python script)
for lst in glob.glob(f"{imageDir}/*.jpg"):
    print(get_sql_insert(lst,sqlFilePath))
