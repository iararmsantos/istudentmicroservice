import React from "react";
import Header from "../../components/Header";
import { Box, useTheme, Typography } from "@mui/material";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { tokens } from "../../theme";

const FAQ = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  return (
    <Box m="20px">
      <Header title="FAQ" subtitle="Frequently Asked Question Pages" />
      <Accordion defaultExpanded>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography color={colors.greenAccent[500]} variant="h4">
            An Important Question 1
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Debitis
            doloremque, accusamus obcaecati at tempora, corporis, iusto earum
            repellat voluptatem ad a. Minus eligendi nihil, voluptatum placeat
            id quisquam nostrum dolores.
          </Typography>
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography color={colors.greenAccent[500]} variant="h4">
            Another Important Question 2
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Debitis
            doloremque, accusamus obcaecati at tempora, corporis, iusto earum
            repellat voluptatem ad a. Minus eligendi nihil, voluptatum placeat
            id quisquam nostrum dolores.
          </Typography>
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography color={colors.greenAccent[500]} variant="h4">
            Favorite Question 3
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Debitis
            doloremque, accusamus obcaecati at tempora, corporis, iusto earum
            repellat voluptatem ad a. Minus eligendi nihil, voluptatum placeat
            id quisquam nostrum dolores.
          </Typography>
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography color={colors.greenAccent[500]} variant="h4">
            Random Question 4
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Debitis
            doloremque, accusamus obcaecati at tempora, corporis, iusto earum
            repellat voluptatem ad a. Minus eligendi nihil, voluptatum placeat
            id quisquam nostrum dolores.
          </Typography>
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography color={colors.greenAccent[500]} variant="h4">
            Last Question 5
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Debitis
            doloremque, accusamus obcaecati at tempora, corporis, iusto earum
            repellat voluptatem ad a. Minus eligendi nihil, voluptatum placeat
            id quisquam nostrum dolores.
          </Typography>
        </AccordionDetails>
      </Accordion>
    </Box>
  );
};

export default FAQ;
