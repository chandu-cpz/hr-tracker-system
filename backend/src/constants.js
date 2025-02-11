import { fileURLToPath } from 'url';
import path from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export const RECOMMENDATIONPROTOFILE_PATH = path.join(__dirname, '../proto/recommendation.proto');
export const ATSPROTOFILE_PATH = path.join(__dirname, '../proto/ats.proto');

export const DB_NAME = "hrdb";

export const GRPC_HOST = "localhost:50052"; 

export const PROTO_FILE_LOAD_OPTIONS = {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true,
};
