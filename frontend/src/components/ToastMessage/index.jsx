import React, { useEffect, useState } from 'react';

import Snackbar from '@mui/material/Snackbar';
import { Alert } from '@mui/material';

export default function ToastMessage({message, isOpen, severity, onClose}) {
    const [open, setOpen] = useState(isOpen);

    useEffect(() => {
        if (isOpen) {
            setOpen(true);
          }
      }, [isOpen]);

  const handleClose = (event, reason) => { 
    if (reason === 'clickaway') {
      return;
    }

    setOpen(false);

    if (onClose) {
        onClose();
    }
  };
    return (
        <div>
            <Snackbar
                anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
                open={open}
                autoHideDuration={3000}
                onClose={handleClose}
            >
                <Alert
                    onClose={handleClose}
                    severity={severity}
                    variant="filled"
                    sx={{ width: '100%' }}
                >{message}</Alert>
            </Snackbar>
        </div>
    );
}