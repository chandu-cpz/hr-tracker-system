// authMiddleware
import { User } from "../models/user.model.js";

export function checkAuth (req, res, next)  {

  const token = req.cookies.token;
  const {userid} = req.body; // Get from cookies
  
  if(!token) {
    return res.status(401).json({message: 'No token in cookies, Please Login'});
  }  
  try {
    const user = User.findOne({_id: userid});
    if(!user) {
      return res.status(401).json({message: 'Invalid user'});
    }
    if(user.checkToken(token)){
      next();
    }
    else {
      return res.status(401).json({message: 'User token is invalid'});
    }

  } catch (err) {
    return res.status(401).json({message: 'Invalid user'});
  }

}