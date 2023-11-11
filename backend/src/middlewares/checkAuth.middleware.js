// authMiddleware
import { User } from "../models/user.model.js";

export async function checkAuth(req, res, next) {
  const token = req.cookies.token;
  const { _id } = req.body;

  if (!token) {
    return res.status(401).json({ message: 'No token in cookies, Please Login' });
  }

  try {
    const user = await User.findOne({ _id: _id });

    if (!user || !user.equals(_id)) {
      return res.status(401).json({ message: 'Invalid user' });
    }

    if (await user.checkToken(token)) {
      next();
    } else {
      return res.status(401).json({ message: 'User token is invalid' });
    }

  } catch (err) {
    console.error(err);
    return res.status(401).json({ message: 'Invalid user' });
  }
}
