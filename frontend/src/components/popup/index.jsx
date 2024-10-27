import { Button, Dialog, DialogContent, DialogTitle, Typography } from '@mui/material';
import React from 'react'

const Popup = (props) => {
  const {title, children, openPopup, setOpenPopup, refresh} = props;

  const handleClose = () => {
    setOpenPopup(false);
    refresh();
  }
  return (
    <Dialog open={openPopup} onClose={handleClose}>
      <DialogTitle sx={{m: 0, padding: 0}}>
        <div style={{display: 'flex'}}>
          <Typography variant='h4' style={{flexGrow: 1}} sx={{mt: "10px", padding: "16px 20px"}}>{title}</Typography>
        <Button color="secondary" variant="outline" onClick={handleClose}>X</Button>
        </div>
      </DialogTitle>
      <DialogContent>{children}</DialogContent>
    </Dialog>
  )
}

export default Popup