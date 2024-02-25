import uvicorn
import table_creation
from fastapi import FastAPI
from post_requests import app as post_app
from get_requests import app as get_app

table_creation.create_tables()

main_app = FastAPI()

main_app.mount("/post", app=post_app)
main_app.mount("/get", app=get_app)


if __name__ == "__main__":
    uvicorn.run(main_app, host="0.0.0.0", port=8000)
