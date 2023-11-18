import express from 'express';
const cloudinaryRouter = express.Router();

import { getSignedUploadPreset } from '../controllers/cloudinary.controller.js';

cloudinaryRouter.get('/:folder', getSignedUploadPreset);

export default cloudinaryRouter;