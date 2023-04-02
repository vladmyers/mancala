# Set the base image to Node.js 18
FROM node:18-alpine

# Set the working directory
WORKDIR /app

# Copy the package.json and package-lock.json files to the working directory
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the source code to the working directory
COPY . .

# Build the app
RUN npm run build

# Expose the port that the app will listen on
EXPOSE 3000

# Start the app
CMD [ "npm", "start" ]
