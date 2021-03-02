FROM node:12.21.0-alpine as node

RUN mkdir -p /app

WORKDIR /app

COPY package.json /app

RUN npm install 

COPY . /app
RUN npm run build --prod

FROM nginx
COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf
COPY --from=node /app/dist/ui /usr/share/nginx/html
