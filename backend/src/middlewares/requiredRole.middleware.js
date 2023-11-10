// Require role middleware
export function requiredRole(requiredRole) {

    return (req, res, next) => {
  
      const { role } = req.body;
  
      if(role !== requiredRole){
        return res.status(401).json({message: 'You are not authorized to perform this action'});
      }
  
      next();
    }
  
  }