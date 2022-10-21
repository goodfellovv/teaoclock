FROM node as build
WORKDIR /frontend-teaoclock

COPY package.json ./
COPY ./src/config.json ./

RUN npm install

COPY . ./

ENV REACT_APP_NAME=frontend-teaoclock

RUN npm run build

FROM nginx
COPY --from=build /frontend-teaoclock/build /usr/share/nginx/html
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]